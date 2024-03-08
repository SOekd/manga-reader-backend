package com.mangareader.compressservice;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CompressService {

    public Mono<Void> compress(Flux<FilePart> filePartFlux, ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.valueOf("application/zip"));
        response.getHeaders().setContentDisposition(ContentDisposition.attachment().filename(UUID.randomUUID() + ".zip").build());

        return filePartFlux.flatMap(filePart -> DataBufferUtils.join(filePart.content())
                        .map(dataBuffer -> {
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            DataBufferUtils.release(dataBuffer);
                            return new CompressData(filePart.filename(), bytes);
                        }))
                .flatMap(imageData -> convertToWebP(imageData.bytes())
                        .map(webpBytes -> new CompressData(imageData.filename(), webpBytes)))
                .collectList()
                .flatMap(this::zipFiles)
                .flatMap(bytes -> response.writeWith(Mono.just(response.bufferFactory().wrap(bytes))));
    }

    private Mono<byte[]> convertToWebP(byte[] imageData) {
        return Mono.fromCallable(() -> {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                BufferedImage image = ImageIO.read(bais);
                if (image == null) {
                    throw new CompressException("Could not read image");
                }

                if (!ImageIO.write(image, "webp", baos)) {
                    throw new CompressException("Could not write image");
                }

                return baos.toByteArray();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<byte[]> zipFiles(List<CompressData> files) {
        return Mono.fromCallable(() -> {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try (ZipOutputStream zos = new ZipOutputStream(baos)) {
                        for (CompressData file : files) {
                            ZipEntry zipEntry = new ZipEntry(extractFileName(file.filename()) + ".webp");
                            zos.putNextEntry(zipEntry);
                            zos.write(file.bytes());
                            zos.closeEntry();
                        }
                    }
                    return baos.toByteArray();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private String extractFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public Mono<Void> compressImage(Mono<FilePart> filePartMono, ServerHttpResponse response) {
        response.getHeaders().setContentDisposition(ContentDisposition.attachment().filename(UUID.randomUUID() + ".webp").build());
        response.getHeaders().add("Content-Type", "image/webp");

        return filePartMono.flatMap(filePart -> DataBufferUtils.join(filePart.content())
                        .map(dataBuffer -> {
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            DataBufferUtils.release(dataBuffer);
                            return bytes;
                        }))
                .flatMap(this::convertToWebP)
                .flatMap(bytes -> response.writeWith(Mono.just(response.bufferFactory().wrap(bytes))));
    }

}

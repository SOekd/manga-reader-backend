package com.mangareader.storage.discord;

import com.mangareader.common.storage.ChunkFile;
import com.mangareader.common.storage.CloudFile;
import com.mangareader.common.storage.CloudFileType;
import com.mangareader.common.storage.util.ChunkUtils;
import com.mangareader.common.storage.util.EncryptionUtils;
import com.mangareader.common.storage.util.HashUtils;
import com.mangareader.storage.discord.exception.DiscordFileCorruptedException;
import com.mangareader.storage.discord.exception.DiscordFileNotFoundException;
import com.mangareader.storage.discord.exception.NotADiscordFileException;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.spec.MessageCreateFields;
import discord4j.core.spec.MessageCreateSpec;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiscordStorageService {

    private static final String DEFAULT_FILE_NAME = "content.txt";

    private static final String DEFAULT_DISCORD_URL = "https://cdn.discordapp.com/attachments/%s/" + DEFAULT_FILE_NAME;

    private static final int CHUNK_SIZE = 25 * 1024 * 1024;

    private final DiscordStorageRepository discordStorageRepository;

    private final GatewayDiscordClient discordClient;

    private final DiscordInformation discordInformation;

    private final WebClient webClient = WebClient.builder().build();

    public Mono<CloudFile> create(Mono<FilePart> filePartMono) {
        return filePartMono
                .flatMap(filePart -> DataBufferUtils.join(filePart.content())
                        .map(dataBuffer -> {
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            DataBufferUtils.release(dataBuffer);
                            return bytes;
                        }))
                .map(bytes -> ChunkUtils.cut(bytes, CHUNK_SIZE))
                .map(ChunkUtils::order)
                .flatMapMany(this::createDiscordPosts)
                .collectList()
                .flatMap(chunkFiles -> discordStorageRepository.save(new CloudFile(null, CloudFileType.DISCORD, chunkFiles)));
    }

    private Flux<ChunkFile> createDiscordPosts(Map<Integer, byte[]> byteMap) {
        val chosenChannel = discordInformation.getRandomChannel();
        return Flux.fromIterable(byteMap.entrySet())
                .flatMap(bytes -> {
                            val password = EncryptionUtils.generatePassword();
                            return discordClient.getChannelById(Snowflake.of(chosenChannel))
                                    .map(Channel::getRestChannel)
                                    .flatMap(channel -> EncryptionUtils.encrypt(bytes.getValue(), password)
                                            .flatMap(encryptedBytes -> channel.createMessage(
                                                    MessageCreateSpec.builder()
                                                            .addFile(MessageCreateFields.File.of(
                                                                    DEFAULT_FILE_NAME,
                                                                    new ByteArrayInputStream(encryptedBytes)
                                                            ))
                                                            .build().asRequest())
                                            )
                                    )
                                    .map(data -> new ChunkFile(
                                            data.channelId().asString() + "/" + data.attachments().get(0).id(),
                                            bytes.getKey(),
                                            password,
                                            HashUtils.createHash(bytes.getValue()))
                                    );
                        }
                );
    }

    public Mono<Void> download(ServerHttpResponse response, String fileId) {
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.getHeaders().setContentDisposition(ContentDisposition.attachment().filename(fileId).build());

        return discordStorageRepository.findById(fileId)
                .switchIfEmpty(Mono.error(new DiscordFileNotFoundException("File with id " + fileId + " not found")))
                .flatMap(cloudFile -> {
                    if (cloudFile.getCloudFileType() != CloudFileType.DISCORD) {
                        return Mono.error(new NotADiscordFileException("File is not a discord file"));
                    }

                    return Mono.just(cloudFile);
                })
                .flatMap(cloudFile -> Flux.fromIterable(cloudFile.getChunkFiles())
                        .flatMap(chunkFile -> Mono.just(String.format(DEFAULT_DISCORD_URL, chunkFile.getId()))
                                .flatMap(url -> webClient.get().uri(url)
                                        .retrieve()
                                        .bodyToFlux(DataBuffer.class)
                                        .collectList()
                                        .flatMap(dataBuffers -> DataBufferUtils.join(Flux.fromIterable(dataBuffers)))
                                        .map(dataBuffer -> {
                                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                            dataBuffer.read(bytes);
                                            DataBufferUtils.release(dataBuffer);
                                            return bytes;
                                        })
                                        .flatMap(encryptedBytes -> EncryptionUtils.decrypt(encryptedBytes, chunkFile.getPassword())
                                                .map(bytes -> Map.entry(chunkFile, bytes)))
                                        )
                        )
                        .collectMap(Map.Entry::getKey, Map.Entry::getValue))
                .flatMap(this::checkHash)
                .map(cloudFileMap -> {
                    List<byte[]> orderedBytes = cloudFileMap.entrySet().stream()
                            .sorted(Comparator.comparingInt(entry -> entry.getKey().getOrder()))
                            .map(Map.Entry::getValue)
                            .toList();
                    return ChunkUtils.merge(orderedBytes);
                })
                .flatMap(bytes -> response.writeWith(Mono.just(response.bufferFactory().wrap(bytes))))
                .then();
    }

    private Mono<Map<ChunkFile, byte[]>> checkHash(Map<ChunkFile, byte[]> cloudFileMap) {
        return Flux.fromIterable(cloudFileMap.entrySet())
                .flatMap(cloudFileEntry -> {
                    val chunkFile = cloudFileEntry.getKey();
                    val bytes = cloudFileEntry.getValue();
                    val hash = HashUtils.createHash(bytes);

                    if (!chunkFile.getHash().equals(hash)) {
                        return Mono.error(new DiscordFileCorruptedException("File with id " + chunkFile.getId() + " is corrupted. Expected hash: " + chunkFile.getHash() + " but got " + hash));
                    }

                    return Mono.just(Map.entry(chunkFile, bytes));
                })
                .collectMap(Map.Entry::getKey, Map.Entry::getValue);
    }


}

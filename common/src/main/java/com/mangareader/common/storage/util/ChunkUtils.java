package com.mangareader.common.storage.util;

import lombok.experimental.UtilityClass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

@UtilityClass
public class ChunkUtils {

    public List<byte[]> cut(byte[] bytes, int chunkSize) {
        int numOfChunks = (bytes.length + chunkSize - 1) / chunkSize;
        List<byte[]> byteChunks = new ArrayList<>(numOfChunks);
        for (int i = 0; i < numOfChunks; ++i) {
            int start = i * chunkSize;
            int end = Math.min(bytes.length, (i + 1) * chunkSize);
            byteChunks.add(Arrays.copyOfRange(bytes, start, end));
        }
        return byteChunks;
    }

    public byte[] merge(List<byte[]> byteChunks) {
        int totalLength = byteChunks.stream().mapToInt(bytes -> bytes.length).sum();
        byte[] bytes = new byte[totalLength];
        int offset = 0;
        for (byte[] byteChunk : byteChunks) {
            System.arraycopy(byteChunk, 0, bytes, offset, byteChunk.length);
            offset += byteChunk.length;
        }
        return bytes;
    }

    public Map<Integer, byte[]> order(List<byte[]> byteChunks) {
        Map<Integer, byte[]> ordered = new HashMap<>();

        for (int i = 0; i < byteChunks.size(); i++) {
            ordered.put(i, byteChunks.get(i));
        }

        return ordered;
    }

}


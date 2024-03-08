package com.mangareader.common.storage.util;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

@UtilityClass
public class EncryptionUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final String ALGORITHM = "AES";
    private final String TRANSFORMATION = "AES/CTR/NoPadding";

    private static SecretKey generateKeyFromPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        keyBytes = Arrays.copyOf(keyBytes, 16); // Use only first 128 bits
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public static String generatePassword() {
        byte[] password = new byte[16];
        RANDOM.nextBytes(password);
        return new String(password, StandardCharsets.UTF_8);
    }

    private static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        RANDOM.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public Mono<byte[]> encrypt(byte[] inputData, String password) {
        return Mono.fromCallable(() -> {
            IvParameterSpec iv = generateIv();
            byte[] encryptedData = doCipher(inputData, password, Cipher.ENCRYPT_MODE, iv);

            ByteBuffer byteBuffer = ByteBuffer.allocate(16 + encryptedData.length);
            byteBuffer.put(iv.getIV());
            byteBuffer.put(encryptedData);
            return byteBuffer.array();
        });
    }

    public Mono<byte[]> decrypt(byte[] encryptedDataWithIv, String password) {
        return Mono.fromCallable(() -> {
            ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedDataWithIv);
            byte[] ivBytes = new byte[16];
            byteBuffer.get(ivBytes);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            byte[] encryptedData = new byte[byteBuffer.remaining()];
            byteBuffer.get(encryptedData);
            return doCipher(encryptedData, password, Cipher.DECRYPT_MODE, iv);
        });
    }

    private byte[] doCipher(byte[] data, String password, int cipherMode, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, generateKeyFromPassword(password), iv);
        return cipher.doFinal(data);
    }
}
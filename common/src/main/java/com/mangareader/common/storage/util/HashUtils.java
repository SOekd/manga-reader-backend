package com.mangareader.common.storage.util;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@UtilityClass
public class HashUtils {

    public String createHash(List<byte[]> dataList) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (byte[] data : dataList) {
                digest.update(data);
            }
            byte[] hash = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while creating hash", e);
        }
    }

    public String createHash(byte[] data) {
        return createHash(List.of(data));
    }

}

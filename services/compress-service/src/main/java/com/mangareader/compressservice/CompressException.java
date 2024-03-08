package com.mangareader.compressservice;

public class CompressException extends RuntimeException {

    public CompressException(String message) {
        super(message);
    }

    public CompressException(String message, Throwable cause) {
        super(message, cause);
    }

}

package com.mangareader.manga.translator.exception;

public class TranslatorAlreadyExistsException extends RuntimeException {

    public TranslatorAlreadyExistsException(String id) {
        super("A translator already exists with this id: " + id);
    }
}

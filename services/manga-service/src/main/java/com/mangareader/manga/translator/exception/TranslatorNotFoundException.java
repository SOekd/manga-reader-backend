package com.mangareader.manga.translator.exception;

public class TranslatorNotFoundException extends RuntimeException {

    public TranslatorNotFoundException(String id) {
        super("Translator not found with id: " + id);
    }

}

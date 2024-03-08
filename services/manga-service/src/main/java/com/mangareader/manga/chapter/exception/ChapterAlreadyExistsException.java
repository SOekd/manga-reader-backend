package com.mangareader.manga.chapter.exception;

public class ChapterAlreadyExistsException extends RuntimeException {

    public ChapterAlreadyExistsException(String id) {
        super("Chapter " + id + " already exists.");
    }

}

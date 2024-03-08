package com.mangareader.manga.chapter.exception;

public class ChapterCorruptedException extends RuntimeException {

    public ChapterCorruptedException(String id) {
        super("Chapter " + id + " is corrupted.");
    }

}

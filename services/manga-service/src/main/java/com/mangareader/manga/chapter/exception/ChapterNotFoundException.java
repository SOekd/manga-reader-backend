package com.mangareader.manga.chapter.exception;

import jakarta.validation.constraints.NotNull;

public class ChapterNotFoundException extends RuntimeException {

    public ChapterNotFoundException(@NotNull String id) {
        super("Could not find chapter " + id);
    }

}

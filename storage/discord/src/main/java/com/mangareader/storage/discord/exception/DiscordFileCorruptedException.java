package com.mangareader.storage.discord.exception;

public class DiscordFileCorruptedException extends RuntimeException {

    public DiscordFileCorruptedException(String message) {
        super(message);
    }

}

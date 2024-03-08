package com.mangareader.storage.discord;

import lombok.Data;

@Data
public class DiscordInformation {

    private final String token;

    private final String[] channels;

    public String getRandomChannel() {
        return channels[(int) (Math.random() * channels.length)];
    }

}

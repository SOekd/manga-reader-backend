package com.mangareader.storage.discord;


import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.gateway.intent.Intent;
import discord4j.gateway.intent.IntentSet;
import discord4j.rest.RestClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscordStorageApplication {


    public static void main(String[] args) {
        SpringApplication.run(DiscordStorageApplication.class, args);
    }

    @Bean
    public GatewayDiscordClient getDiscordClient(DiscordInformation discordInformation) {
        return DiscordClientBuilder.create(discordInformation.getToken()).build()
                .gateway()
                .setEnabledIntents(IntentSet.of(Intent.GUILD_MESSAGES))
                .setInitialPresence(presence -> ClientPresence.doNotDisturb(ClientActivity.of(Activity.Type.STREAMING, "...", null)))
                .login()
                .block();
    }

    @Bean
    public RestClient getRestClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }

    @Bean
    public DiscordInformation getDiscordToken() {
        String token = System.getenv("DISCORD_TOKEN");

        String unsolvedChannels = System.getenv("DISCORD_CHANNELS");

        String[] channels = unsolvedChannels.split(",");

        return new DiscordInformation(token, channels);
    }

}

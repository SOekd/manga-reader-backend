package com.mangareader.compressservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CompressApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompressApplication.class, args);
    }

}

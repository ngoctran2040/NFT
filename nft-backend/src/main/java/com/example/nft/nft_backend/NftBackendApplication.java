package com.example.nft.nft_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

// Quan trọng: exclude để tắt tự động cấu hình, bắt buộc dùng MongoConfig của bạn
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class NftBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NftBackendApplication.class, args);
    }

}
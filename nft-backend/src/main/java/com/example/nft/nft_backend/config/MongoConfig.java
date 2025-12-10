package com.example.nft.nft_backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    // CHUỖI KẾT NỐI TỪ FILE CẤU HÌNH CỦA BẠN
    private final String connectionString = "mongodb+srv://groupDB:groupDB@cluster0.rfblthc.mongodb.net/NFT?retryWrites=true&w=majority&appName=Cluster0";

    @Override
    protected String getDatabaseName() {
        return "NFT"; // Tên database
    }

    @Override
    public MongoClient mongoClient() {
        // TỰ TẠO MongoClient bằng chuỗi URI cứng để bỏ qua lỗi parsing
        return MongoClients.create(connectionString);
    }
}
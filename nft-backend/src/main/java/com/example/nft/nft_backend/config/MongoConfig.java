package com.example.nft.nft_backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.nft.nft_backend.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final String connectionString =
            "mongodb+srv://groupDB:groupDB@cluster0.rfblthc.mongodb.net/NFT?retryWrites=true&w=majority&appName=Cluster0";

    @Override
    protected String getDatabaseName() {
        return "NFT";
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(connectionString);
    }
}

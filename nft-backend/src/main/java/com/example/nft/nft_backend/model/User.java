package com.example.nft.nft_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    /**
     * Role: USER | ADMIN
     * Mặc định USER
     */
    private String role = "USER";

    /**
     * Địa chỉ ví (unique)
     */
    @Indexed(unique = true)
    private String walletAddress;

    /**
     * Nonce dùng cho MetaMask login
     */
    private String nonce;

    private Instant nonceUpdatedAt;

    /**
     * Thông tin profile
     */
    private String username;
    private String email;
    private String bio;
    private String profileImageUrl;
    private String bannerImageUrl;

    /**
     * Thời gian tạo user
     */
    private Instant createdAt = Instant.now();
}

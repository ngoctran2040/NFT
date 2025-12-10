package com.example.nft.nft_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Lombok tự tạo Getters, Setters, toString...
@NoArgsConstructor // Tạo Constructor không tham số
@AllArgsConstructor // Tạo Constructor đầy đủ tham số
@Document(collection = "users") // Tên bảng trong MongoDB sẽ là "users"
public class User {

    @Id // Khóa chính (Primary Key) tự sinh của MongoDB
    private String id;

    @Indexed(unique = true) // Đảm bảo địa chỉ ví là duy nhất, không trùng lặp
    private String walletAddress;

    private String username;
    private String email;
    private String bio; // Giới thiệu bản thân
    private String profileImageUrl; // Link ảnh đại diện
    private String bannerImageUrl; // Link ảnh bìa

    private LocalDateTime createdAt = LocalDateTime.now(); // Thời gian tạo
}
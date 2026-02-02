package com.example.nft.nft_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "nfts")
public class NftItem {

    @Id
    private String id;

    // Token ID trên Blockchain
    @Indexed
    private String tokenId;

    // Địa chỉ Smart Contract
    private String contractAddress;

    private String name;
    private String description;

    // URL hình ảnh
    private String imageUrl;

    // URL metadata
    private String metadataUrl;

    // Giá NFT
    private Double price;

    // Artist
    private String creatorAddress;

    // Chủ sở hữu hiện tại
    private String ownerAddress;

    // ✅ ĐỔI TÊN – ĐIỂM MẤU CHỐT
    // true = đang bán, false = không bán
    private boolean listed;

    private LocalDateTime createdAt = LocalDateTime.now();
}

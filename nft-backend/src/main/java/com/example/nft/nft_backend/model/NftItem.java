package com.example.nft.nft_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "nfts") // Tên bảng trong MongoDB là "nfts"
public class NftItem {

    @Id
    private String id;

    // Token ID trên Blockchain (quan trọng để mapping)
    @Indexed
    private String tokenId; 
    
    // Địa chỉ Smart Contract chứa NFT này
    private String contractAddress;

    private String name;
    private String description;
    
    // URL đến hình ảnh lưu trên IPFS hoặc Server
    private String imageUrl; 
    
    // URL đến file Metadata gốc trên IPFS
    private String metadataUrl;

    // Giá hiện tại (ETH/BNB...)
    private Double price; 

    // Người tạo ra NFT (Artist)
    private String creatorAddress;

    // Người sở hữu hiện tại
    private String ownerAddress;

    // Trạng thái: true = đang rao bán, false = chỉ để trưng bày
    private boolean isListed; 

    private LocalDateTime createdAt = LocalDateTime.now();
}
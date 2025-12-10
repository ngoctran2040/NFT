package com.example.nft.nft_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "activities")
public class Activity {

    @Id
    private String id;

    // ID của NFT liên quan
    private String nftItemId;

    // Loại hoạt động: "MINT", "LIST", "SALE", "TRANSFER", "CANCEL_LIST"
    private String type; 

    private String fromAddress; // Người gửi
    private String toAddress;   // Người nhận
    
    private Double price;       // Giá giao dịch (nếu có)
    
    private String transactionHash; // Mã giao dịch trên Blockchain (TxHash)

    private LocalDateTime timestamp = LocalDateTime.now();
}
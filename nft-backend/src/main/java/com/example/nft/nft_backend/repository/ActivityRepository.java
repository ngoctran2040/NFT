package com.example.nft.nft_backend.repository;

import com.example.nft.nft_backend.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

    // 1. Lấy lịch sử giao dịch của một NFT cụ thể (Sắp xếp mới nhất trước)
    // Dùng để hiển thị tab "History" trong trang chi tiết NFT
    List<Activity> findByNftItemIdOrderByTimestampDesc(String nftItemId);

    // 2. Lấy hoạt động liên quan đến một ví cụ thể (Người này đã mua/bán gì?)
    List<Activity> findByFromAddressOrToAddress(String fromAddress, String toAddress);
}
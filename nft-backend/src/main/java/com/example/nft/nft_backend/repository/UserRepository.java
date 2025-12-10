package com.example.nft.nft_backend.repository;

import com.example.nft.nft_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // 1. Tìm người dùng dựa trên địa chỉ ví (Dùng khi đăng nhập)
    // Trả về Optional vì có thể không tìm thấy user nào
    Optional<User> findByWalletAddress(String walletAddress);

    // 2. Kiểm tra xem địa chỉ ví này đã đăng ký chưa (Trả về True/False)
    Boolean existsByWalletAddress(String walletAddress);
}
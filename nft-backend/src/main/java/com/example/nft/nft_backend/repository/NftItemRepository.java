package com.example.nft.nft_backend.repository;

import com.example.nft.nft_backend.model.NftItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NftItemRepository extends MongoRepository<NftItem, String> {

    // 1. Tìm một NFT cụ thể dựa trên TokenID và Contract Address
    // (Vì TokenID=1 có thể tồn tại ở nhiều Contract khác nhau)
    Optional<NftItem> findByTokenIdAndContractAddress(String tokenId, String contractAddress);

    // 2. Lấy danh sách NFT mà một người dùng đang sở hữu (Trang Profile > Owned)
    List<NftItem> findByOwnerAddress(String ownerAddress);

    // 3. Lấy danh sách NFT mà một người dùng đã tạo ra (Trang Profile > Created)
    List<NftItem> findByCreatorAddress(String creatorAddress);

    // 4. Lấy tất cả NFT đang được rao bán (Trang chủ Marketplace)
    // isListed = true
    List<NftItem> findByIsListedTrue();
}
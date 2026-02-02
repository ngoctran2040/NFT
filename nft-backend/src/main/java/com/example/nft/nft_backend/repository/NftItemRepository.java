package com.example.nft.nft_backend.repository;

import com.example.nft.nft_backend.model.NftItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NftItemRepository extends MongoRepository<NftItem, String> {

    // 1. Tìm NFT theo tokenId + contract
    Optional<NftItem> findByTokenIdAndContractAddress(
            String tokenId,
            String contractAddress
    );

    // 2. NFT user đang sở hữu
    List<NftItem> findByOwnerAddress(String ownerAddress);

    // 3. NFT user đã tạo
    List<NftItem> findByCreatorAddress(String creatorAddress);

    // 4. NFT đang bán (Marketplace)
    // ✅ KHÔNG DÙNG findByIsListedTrue
    List<NftItem> findByListedTrue();
}

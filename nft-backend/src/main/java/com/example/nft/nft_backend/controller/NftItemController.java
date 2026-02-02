package com.example.nft.nft_backend.controller;

import com.example.nft.nft_backend.model.NftItem;
import com.example.nft.nft_backend.repository.NftItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nfts")
@CrossOrigin(origins = "*")
public class NftItemController {

    @Autowired
    private NftItemRepository nftItemRepository;

    // ✅ Xem tất cả NFT
    @GetMapping
    public List<NftItem> getAllNFTs() {
        return nftItemRepository.findAll();
    }

    // ✅ MARKET – PUBLIC
    @GetMapping("/market")
    public List<NftItem> getMarketItems() {
        // ❗ DÒNG NÀY PHẢI LÀ findByListedTrue
        return nftItemRepository.findByListedTrue();
    }

    // ✅ Xem chi tiết NFT
    @GetMapping("/{id}")
    public ResponseEntity<NftItem> getNftById(@PathVariable String id) {
        Optional<NftItem> nft = nftItemRepository.findById(id);
        return nft.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
}

package com.example.nft.nft_backend.controller;

import com.example.nft.nft_backend.model.NftItem;
import com.example.nft.nft_backend.repository.NftItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Đánh dấu đây là nơi xử lý API
@RequestMapping("/api/nfts") // Tất cả API trong này sẽ bắt đầu bằng /api/nfts
@CrossOrigin(origins = "*") // Cho phép Frontend (React) truy cập mà không bị chặn
public class NftItemController {

    @Autowired
    private NftItemRepository nftItemRepository; // Gọi "Thủ kho" NFT

    // 1. Lấy tất cả NFT (Dùng cho trang khám phá)
    @GetMapping
    public List<NftItem> getAllNfts() {
        return nftItemRepository.findAll();
    }

    // 2. Lấy danh sách NFT đang rao bán (Marketplace)
    @GetMapping("/market")
    public List<NftItem> getMarketItems() {
        return nftItemRepository.findByIsListedTrue();
    }

    // 3. Lấy chi tiết 1 NFT theo ID (Trang Detail)
    @GetMapping("/{id}")
    public ResponseEntity<NftItem> getNftById(@PathVariable String id) {
        Optional<NftItem> nft = nftItemRepository.findById(id);
        // Nếu tìm thấy thì trả về OK, không thấy thì trả về lỗi 404
        return nft.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // 4. Tạo NFT mới (Sau khi Mint trên Blockchain xong thì gọi cái này để lưu vào DB)
    @PostMapping("/create")
    public NftItem createNft(@RequestBody NftItem nftItem) {
        return nftItemRepository.save(nftItem);
    }

    // 5. Lấy NFT của một người dùng cụ thể (Trang Profile > My NFTs)
    @GetMapping("/owner/{ownerAddress}")
    public List<NftItem> getNftsByOwner(@PathVariable String ownerAddress) {
        return nftItemRepository.findByOwnerAddress(ownerAddress);
    }
}
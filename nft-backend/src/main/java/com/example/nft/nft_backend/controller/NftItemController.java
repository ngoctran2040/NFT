package com.example.nft.nft_backend.controller;

import com.example.nft.nft_backend.dto.AdminAuthDTO;
import com.example.nft.nft_backend.model.NftItem;
import com.example.nft.nft_backend.model.User;
import com.example.nft.nft_backend.repository.NftItemRepository;
import com.example.nft.nft_backend.repository.UserRepository;
import com.example.nft.nft_backend.util.EthSig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nfts")
@CrossOrigin(origins = "*")
public class NftItemController {

    @Autowired private NftItemRepository nftItemRepository;
    @Autowired private UserRepository userRepository;

    // ✅ ai cũng xem được
    @GetMapping
    public List<NftItem> getAllNfts() {
        return nftItemRepository.findAll();
    }

    // ✅ ai cũng xem được (market)
    @GetMapping("/market")
    public List<NftItem> getMarketItems() {
        return nftItemRepository.findByIsListedTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NftItem> getNftById(@PathVariable String id) {
        Optional<NftItem> nft = nftItemRepository.findById(id);
        return nft.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ===== Admin only =====
    private void requireAdmin(AdminAuthDTO auth) {
        try {
            String recovered = EthSig.recoverAddress(auth.getMessage(), auth.getSignature());
            String addr = auth.getWalletAddress().toLowerCase();

            if (!recovered.equalsIgnoreCase(addr)) {
                throw new RuntimeException("Invalid signature");
            }

            User u = userRepository.findByWalletAddress(addr)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (u.getRole() == null || !u.getRole().equalsIgnoreCase("ADMIN")) {
                throw new RuntimeException("Admin only");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ✅ admin mới tạo được
    @PostMapping("/create")
    public ResponseEntity<?> createNft(
            @RequestBody NftItem nftItem,
            @RequestHeader("x-wallet") String wallet,
            @RequestHeader("x-message") String message,
            @RequestHeader("x-signature") String signature
    ) {
        AdminAuthDTO auth = new AdminAuthDTO();
        auth.setWalletAddress(wallet);
        auth.setMessage(message);
        auth.setSignature(signature);

        requireAdmin(auth);

        return ResponseEntity.ok(nftItemRepository.save(nftItem));
    }
}

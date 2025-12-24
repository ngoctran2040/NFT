package com.example.nft.nft_backend.controller;

import com.example.nft.nft_backend.model.User;
import com.example.nft.nft_backend.repository.UserRepository;
import com.example.nft.nft_backend.util.EthSig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ====== nonce helpers ======
    private static final SecureRandom rnd = new SecureRandom();

    private String randomNonce() {
        byte[] b = new byte[16];
        rnd.nextBytes(b);
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x));
        return sb.toString();
    }

    // 1) Lấy thông tin User theo địa chỉ ví
    @GetMapping("/{walletAddress}")
    public ResponseEntity<User> getUserByWallet(@PathVariable String walletAddress) {
        Optional<User> user = userRepository.findByWalletAddress(walletAddress.toLowerCase());
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 2) Cấp nonce để frontend ký bằng MetaMask
    @GetMapping("/nonce/{walletAddress}")
    public ResponseEntity<Map<String, String>> getNonce(@PathVariable String walletAddress) {
        String addr = walletAddress.toLowerCase();
        String nonce = randomNonce();

        User user = userRepository.findByWalletAddress(addr).orElseGet(User::new);
        user.setWalletAddress(addr);
        user.setNonce(nonce);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("walletAddress", addr, "nonce", nonce));
    }

    // DTO cho login ký
    public static class LoginReq {
        public String walletAddress;
        public String message;
        public String signature;
    }

    // 3) Login bằng chữ ký MetaMask (verify signature)
    @PostMapping("/login")
    public ResponseEntity<User> loginWithSignature(@RequestBody LoginReq req) throws Exception {
        String addr = req.walletAddress.toLowerCase();

        User user = userRepository.findByWalletAddress(addr)
                .orElseThrow(() -> new RuntimeException("Call GET /api/users/nonce/{walletAddress} first"));

        // message phải chứa nonce hiện tại
        if (user.getNonce() == null || req.message == null || !req.message.contains("Nonce: " + user.getNonce())) {
            return ResponseEntity.status(401).build();
        }

        // recover address từ signature
        String recovered = EthSig.recoverAddress(req.message, req.signature);
        if (!recovered.equals(addr)) {
            return ResponseEntity.status(401).build();
        }

        // rotate nonce chống replay
        user.setNonce(randomNonce());
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    // 4) Update profile (giữ như bạn cũ)
    @PutMapping("/update/{walletAddress}")
    public User updateProfile(@PathVariable String walletAddress, @RequestBody User userDetails) {
        User user = userRepository.findByWalletAddress(walletAddress.toLowerCase())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDetails.getUsername());
        user.setBio(userDetails.getBio());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }
}

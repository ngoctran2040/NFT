package com.example.nft.nft_backend.controller;

import com.example.nft.nft_backend.model.User;
import com.example.nft.nft_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 1. Lấy thông tin User theo địa chỉ ví
    @GetMapping("/{walletAddress}")
    public ResponseEntity<User> getUserByWallet(@PathVariable String walletAddress) {
        Optional<User> user = userRepository.findByWalletAddress(walletAddress);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // 2. Login (Code cũ...)
    @PostMapping("/login")
    public User loginOrRegister(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByWalletAddress(user.getWalletAddress());
        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            return userRepository.save(user);
        }
    }
    
    // 3. Update (Code cũ...)
    @PutMapping("/update/{walletAddress}")
    public User updateProfile(@PathVariable String walletAddress, @RequestBody User userDetails) {
        User user = userRepository.findByWalletAddress(walletAddress)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setUsername(userDetails.getUsername());
        user.setBio(userDetails.getBio());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }
}
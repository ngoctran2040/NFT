package com.example.nft.nft_backend.controller;

import com.example.nft.nft_backend.model.Activity;
import com.example.nft.nft_backend.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    // 1. Lưu một hoạt động mới (Ví dụ: Vừa có người mua NFT)
    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityRepository.save(activity);
    }

    // 2. Xem lịch sử của một NFT cụ thể
    @GetMapping("/nft/{nftItemId}")
    public List<Activity> getHistoryByNft(@PathVariable String nftItemId) {
        return activityRepository.findByNftItemIdOrderByTimestampDesc(nftItemId);
    }
}
package com.example.nft.nft_backend.dto;

public class AdminAuthDTO {
    private String walletAddress;
    private String message;
    private String signature;

    public String getWalletAddress() { return walletAddress; }
    public void setWalletAddress(String walletAddress) { this.walletAddress = walletAddress; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }
}

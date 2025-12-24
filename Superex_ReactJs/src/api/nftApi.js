// src/api/nftApi.js

const API_BASE_URL = "http://localhost:8085";

// Hàm xử lý response chung
async function handleResponse(res) {
  const contentType = res.headers.get("content-type");

  if (!res.ok) {
    const text = await res.text(); // debug lỗi backend
    throw new Error(`HTTP ${res.status}: ${text}`);
  }

  // Backend gửi HTML → tránh lỗi JSON.parse
  if (!contentType || !contentType.includes("application/json")) {
    const text = await res.text();
    throw new Error(`Invalid JSON response: ${text.slice(0, 80)}...`);
  }

  return res.json();
}

// ================================
// LẤY TẤT CẢ NFT
// ================================
export async function fetchAllNfts() {
  const res = await fetch(`${API_BASE_URL}/api/nfts`, {
    headers: { Accept: "application/json" },
  });

  return handleResponse(res);
}

// ================================
// LẤY NFT ĐANG RAO BÁN (market)
// ================================
export async function fetchMarketNfts() {
  const res = await fetch(`${API_BASE_URL}/api/nfts/market`, {
    headers: { Accept: "application/json" },
  });

  return handleResponse(res);
}

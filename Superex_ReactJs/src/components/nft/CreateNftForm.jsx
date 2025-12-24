import React, { useState } from "react";
import { uploadToCloudinary } from "../../services/uploadImage";

const API_BASE_URL = "http://localhost:8085";

export default function CreateNftForm({ walletAddress, onCreated }) {
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [isListed, setIsListed] = useState(true);
  const [file, setFile] = useState(null);

  const [submitting, setSubmitting] = useState(false);
  const [msg, setMsg] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMsg("");

    if (!walletAddress) return setMsg("Bạn cần connect MetaMask trước.");
    if (!name.trim()) return setMsg("Vui lòng nhập tên NFT.");
    if (!file) return setMsg("Vui lòng chọn ảnh.");

    try {
      setSubmitting(true);

      // 1) Upload ảnh lên Cloudinary
      const imageUrl = await uploadToCloudinary(file);

      // 2) Lưu NFT vào MongoDB qua backend
      const res = await fetch(`${API_BASE_URL}/api/nfts/create`, {
        method: "POST",
        headers: { "Content-Type": "application/json", Accept: "application/json" },
        body: JSON.stringify({
          name,
          price: Number(price || 0),
          isListed,
          ownerAddress: walletAddress.toLowerCase(),
          imageUrl, // ✅ lưu link ảnh
        }),
      });

      if (!res.ok) {
        const text = await res.text();
        throw new Error(`Create failed: ${text}`);
      }

      const created = await res.json();
      setMsg("✅ Tạo NFT thành công!");
      setName("");
      setPrice("");
      setIsListed(true);
      setFile(null);

      onCreated?.(created);
    } catch (err) {
      console.error(err);
      setMsg(err.message || "Có lỗi xảy ra");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="card p-3 shadow-sm rounded-md">
      <h5 className="mb-3">Create NFT</h5>

      <form onSubmit={handleSubmit}>
        <div className="mb-2">
          <label className="form-label">Tên NFT</label>
          <input className="form-control" value={name} onChange={(e) => setName(e.target.value)} />
        </div>

        <div className="mb-2">
          <label className="form-label">Giá (ETH)</label>
          <input
            className="form-control"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            placeholder="0.1"
          />
        </div>

        <div className="mb-2">
          <label className="form-label">Ảnh</label>
          <input
            className="form-control"
            type="file"
            accept="image/*"
            onChange={(e) => setFile(e.target.files?.[0] || null)}
          />
        </div>

        <div className="form-check mb-3">
          <input
            className="form-check-input"
            type="checkbox"
            checked={isListed}
            onChange={(e) => setIsListed(e.target.checked)}
            id="isListed"
          />
          <label className="form-check-label" htmlFor="isListed">
            Đăng bán (isListed)
          </label>
        </div>

        <button className="btn btn-primary" disabled={submitting}>
          {submitting ? "Đang xử lý..." : "Create"}
        </button>

        {msg && <div className="mt-2">{msg}</div>}
      </form>
    </div>
  );
}

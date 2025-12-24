// src/services/uploadImage.js
export async function uploadToCloudinary(file) {
  const CLOUD_NAME = "da15uzncu";
  const UPLOAD_PRESET = "nft_unsigned";

  const formData = new FormData();
  formData.append("file", file);
  formData.append("upload_preset", UPLOAD_PRESET);

  const res = await fetch(`https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`, {
    method: "POST",
    body: formData,
  });

  if (!res.ok) {
    const text = await res.text();
    throw new Error(`Upload failed: ${text}`);
  }

  const data = await res.json();
  return data.secure_url; // ✅ link ảnh để lưu MongoDB
}

// src/services/metamask.js
import { ethers } from "ethers";
import axios from "axios";

const API = "http://localhost:8085";

export async function metamaskLogin() {
  if (!window.ethereum) throw new Error("Bạn chưa cài MetaMask");

  // ✅ ethers v5 dùng Web3Provider
  const provider = new ethers.providers.Web3Provider(window.ethereum);
  await provider.send("eth_requestAccounts", []);

  const signer = provider.getSigner();
  const address = (await signer.getAddress()).toLowerCase();

  const { data: nonceRes } = await axios.get(`${API}/api/users/nonce/${address}`);
  const nonce = nonceRes.nonce;

  const message = `Login to NFT App\nAddress: ${address}\nNonce: ${nonce}`;
  const signature = await signer.signMessage(message);

  const { data: user } = await axios.post(`${API}/api/users/login`, {
    walletAddress: address,
    message,
    signature,
  });

  return { address, user };
}

package com.example.nft.nft_backend.util;


import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class EthSig {

    public static String recoverAddress(String message, String signatureHex) throws Exception {
        byte[] msgBytes = message.getBytes(StandardCharsets.UTF_8);

        String prefix = "\u0019Ethereum Signed Message:\n" + msgBytes.length;
        byte[] prefixed = (prefix + message).getBytes(StandardCharsets.UTF_8);

        byte[] msgHash = Hash.sha3(prefixed);
        byte[] sig = Numeric.hexStringToByteArray(signatureHex);

        if (sig.length != 65) {
            throw new IllegalArgumentException("Invalid signature length");
        }

        byte v = sig[64];
        if (v < 27) v += 27;

        byte[] r = new byte[32];
        byte[] s = new byte[32];
        System.arraycopy(sig, 0, r, 0, 32);
        System.arraycopy(sig, 32, s, 0, 32);

        Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
        BigInteger publicKey = Sign.signedMessageHashToKey(msgHash, signatureData);

        return ("0x" + Keys.getAddress(publicKey)).toLowerCase();
    }
}


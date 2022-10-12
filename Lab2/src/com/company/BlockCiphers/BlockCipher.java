package com.company.BlockCiphers;

public interface BlockCipher {
    String encrypt(String plaintext);

    String decrypt(String cipherBytes);
}

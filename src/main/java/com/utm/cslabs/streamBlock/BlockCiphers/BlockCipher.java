package com.utm.cslabs.streamBlock.BlockCiphers;

public interface BlockCipher {
    String encrypt(String plaintext);

    String decrypt(String cipherBytes);
}

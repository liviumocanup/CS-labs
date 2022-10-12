package com.company.StreamCiphers;

public interface StreamCipher {
    int BYTE_VALUES = 256;

    byte[] encrypt(String plaintext);

    String decrypt(byte[] cipherBytes);
}

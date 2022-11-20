package com.utm.cslabs.classic.interfaceCipher;

public interface Cipher {
    int ALPHABET_SIZE = 26;

    String encrypt(final String message);

    String decrypt(final String message);
}

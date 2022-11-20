package com.utm.cslabs.classic.implementations;

import com.utm.cslabs.classic.interfaceCipher.Cipher;

public class Xor implements Cipher {
    private final char key;

    public Xor(char key) {
        this.key = key;
    }

    @Override
    public String encrypt(String message) {
        return xorCipher(message);
    }

    @Override
    public String decrypt(String message) {
        return xorCipher(message);
    }

    private String xorCipher(String message) {
        StringBuilder outputString = new StringBuilder();

        int len = message.length();

        for (int i = 0; i < len; i++) {
            outputString.append((char) (message.charAt(i) ^ key));
        }

        return outputString.toString();
    }
}

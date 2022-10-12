package com.company.StreamCiphers;

import java.nio.charset.StandardCharsets;

public class RC4 implements StreamCipher {
    private final byte[] S = new byte[BYTE_VALUES];
    private final byte[] key;
    private byte[] K;

    private int x = 0;
    private int y = 0;

    public RC4(String strKey) {
        strKey = toBinaryString(strKey);
        key = strKey.getBytes(StandardCharsets.US_ASCII);
        ksa();
    }

    // Key-Scheduling Algorithm
    private void ksa() {
        int keyLength = key.length;

        for (int i = 0; i < BYTE_VALUES; i++) {
            S[i] = (byte) i;
        }

        int j = 0;
        for (int i = 0; i < BYTE_VALUES; i++) {
            j = (j + unsignedToBytes(S[i]) + unsignedToBytes(key[i % keyLength])) % BYTE_VALUES;
            byte tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;

        }
    }

    //Pseudo-random generation algorithm
    private byte prga() {
        x = (x + 1) % BYTE_VALUES;
        y = (y + unsignedToBytes(S[x])) % BYTE_VALUES;

        byte temp = S[x];
        S[x] = S[y];
        S[y] = temp;

        return S[(unsignedToBytes(S[x]) + unsignedToBytes(S[y])) % BYTE_VALUES];
    }

    @Override
    public byte[] encrypt(String plaintext) {
        byte[] plainTextToByteArray = plaintext.getBytes(StandardCharsets.US_ASCII);
        K = generateKey(plainTextToByteArray.length);
        return cipher(plainTextToByteArray);
    }

    @Override
    public String decrypt(byte[] encryptedText) {
        return new String(cipher(encryptedText));
    }

    private byte[] cipher(byte[] text) {
        byte[] cipher = new byte[text.length];

        for (int m = 0; m < text.length; m++) {
            cipher[m] = (byte) (text[m] ^ K[m]);
        }

        return cipher;
    }

    private byte[] generateKey(int len) {
        byte[] K = new byte[len];
        for (int i = 0; i < len; i++) {
            K[i] = prga();
        }
        return K;
    }

    private int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    private String toBinaryString(String originalKey) {
        byte[] stringToByteArray = originalKey.getBytes(StandardCharsets.US_ASCII);
        return convertByteArrayToBinaryString(stringToByteArray);
    }

    private String convertByteArrayToBinaryString(byte[] input) {

        StringBuilder result = new StringBuilder();
        for (byte b : input) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                result.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return result.toString();
    }
}
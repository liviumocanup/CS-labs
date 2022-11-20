package com.utm.cslabs.classic.implementations;

import com.utm.cslabs.classic.interfaceCipher.Cipher;

public class Vigenere implements Cipher {
    private String key;

    public Vigenere(String key) {
        this.key = key.toUpperCase();
    }

    @Override
    public String encrypt(String message) {
        circularlyRepeatKey(message);
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            if (Character.isLetter(currentChar)) {
                boolean isLower = Character.isLowerCase(message.charAt(i));

                char x = (char) (((Character.toUpperCase(message.charAt(i)) + key.charAt(i)) % Cipher.ALPHABET_SIZE) + 'A');

                encryptedMessage.append(isLower ? Character.toLowerCase(x) : x);
            } else encryptedMessage.append(currentChar);
        }
        return encryptedMessage.toString();
    }

    @Override
    public String decrypt(String message) {
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length() && i < key.length(); i++) {
            char currentChar = message.charAt(i);
            if (Character.isLetter(currentChar)) {
                boolean isLower = Character.isLowerCase(currentChar);

                char x = (char) (((Character.toUpperCase(currentChar) - key.charAt(i) + Cipher.ALPHABET_SIZE) % Cipher.ALPHABET_SIZE) + 'A');

                decryptedMessage.append(isLower ? Character.toLowerCase(x) : x);
            } else decryptedMessage.append(currentChar);
        }
        return decryptedMessage.toString();
    }

    private void circularlyRepeatKey(String text) {
        int len = text.length();
        StringBuilder keyBuilder = new StringBuilder(this.key);

        for (int i = 0; ; i++) {
            if (key.length() >= len)
                i = 0;
            if (keyBuilder.length() == text.length())
                break;
            keyBuilder.append(keyBuilder.charAt(i));
        }

        this.key = keyBuilder.toString();
    }
}

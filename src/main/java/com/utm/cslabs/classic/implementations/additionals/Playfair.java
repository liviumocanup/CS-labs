package com.utm.cslabs.classic.implementations.additionals;

import com.utm.cslabs.classic.interfaceCipher.Cipher;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

class Playfair implements Cipher {
    private final char[][] keyMatrix = new char[5][5];
    private String key;

    public Playfair(String key) {
        this.key = key.toLowerCase();
        removeDuplicatesFromKey();
        generateCipherKey();
    }

    @Override
    public String decrypt(String message) {
        return encrypt(message);
    }

    @Override
    public String encrypt(String message) {
        message = formatPlainText(message);
        String[] messagePairs = formPairs(message);
        StringBuilder encryptedText = new StringBuilder();

        for (String pair : messagePairs) {
            char ch1 = pair.charAt(0);
            char ch2 = pair.charAt(1);
            int[] pos1 = getCharPos(ch1);
            int[] pos2 = getCharPos(ch2);

            if (pos1[0] == pos2[0]) {
                pos1[1] = (pos1[1] + 1) % 5;
                pos2[1] = (pos2[1] + 1) % 5;
            } else if (pos1[1] == pos2[1]) {
                pos1[0] = (pos1[0] + 1) % 5;
                pos2[0] = (pos2[0] + 1) % 5;
            } else {
                int temp = pos1[1];
                pos1[1] = pos2[1];
                pos2[1] = temp;
            }
            encryptedText.append(keyMatrix[pos1[0]][pos1[1]]).append(keyMatrix[pos2[0]][pos2[1]]);
        }

        return encryptedText.toString();
    }

    private String formatPlainText(String message) {
        StringBuilder text = new StringBuilder();
        int len = message.length();

        for (int i = 0; i < len; i++) {
            if (message.charAt(i) == 'j')
                text.append('i');
            else
                text.append(message.charAt(i));
        }

        for (int i = 0; i < text.length() - 1; i += 2) {
            if (text.charAt(i) == text.charAt(i + 1))
                text = new StringBuilder(text.substring(0, i + 1) + 'x' + text.substring(i + 1));
        }

        if (len % 2 == 1)
            text.append('x');
        return text.toString();
    }

    private String[] formPairs(String text) {
        int len = text.length();
        String[] pairs = new String[len / 2];

        for (int i = 0, count = 0; i < len / 2; i++)
            pairs[i] = text.substring(count, count += 2);

        return pairs;
    }

    private int[] getCharPos(char ch) {
        int[] keyPos = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if (keyMatrix[i][j] == ch) {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    break;
                }
            }
        }
        return keyPos;
    }

    private void removeDuplicatesFromKey() {
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        StringBuilder newKey = new StringBuilder();

        for (int i = 0; i < key.length(); i++)
            set.add(key.charAt(i));
        for (Character character : set)
            newKey.append(character);

        key = newKey.toString();
    }

    private void generateCipherKey() {
        StringBuilder tempKey = new StringBuilder(key);
        addLettersToKey(tempKey);

        for (int i = 0, idx = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                keyMatrix[i][j] = tempKey.charAt(idx++);
    }

    private void addLettersToKey(StringBuilder tempKey) {
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == 'j')
                continue;
            set.add(key.charAt(i));
        }

        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 97);
            if (ch == 'j')
                continue;
            if (!set.contains(ch))
                tempKey.append(ch);
        }
    }
}
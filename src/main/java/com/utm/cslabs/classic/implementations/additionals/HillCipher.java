package com.utm.cslabs.classic.implementations.additionals;

public class HillCipher {
    private final String key;

    public HillCipher(String key) {
        this.key = key;
    }

    private void getKeyMatrix(String key, int[][] keyMatrix, int len) {
        int k = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                keyMatrix[i][j] = (key.charAt(k)) % 65;
                k++;
            }
        }
    }

    public String encrypt(String message) {
        int n = message.length();
        int[][] keyMatrix = new int[n][n];
        getKeyMatrix(key, keyMatrix, n);
        int[][] messageVector = new int[n][1];

        for (int i = 0; i < n; i++)
            messageVector[i][0] = (message.charAt(i)) % 65;

        int[][] cipherMatrix = new int[n][1];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {
                cipherMatrix[i][j] = 0;

                for (int x = 0; x < 3; x++) {
                    cipherMatrix[i][j] += keyMatrix[i][x] * messageVector[x][j];
                }

                cipherMatrix[i][j] = cipherMatrix[i][j] % 26;
            }
        }

        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < n; i++)
            encryptedMessage.append((char) (cipherMatrix[i][0] + 65));

        return encryptedMessage.toString();
    }
}

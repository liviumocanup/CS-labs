package implementations;

import interfaceCipher.Cipher;

public class Caesar implements Cipher {
    private final int key;

    public Caesar(int key) {
        this.key = key;
    }

    @Override
    public String encrypt(String text) {
        return cipher(text, this.key);
    }

    @Override
    public String decrypt(String text) {
        return cipher(text, Cipher.ALPHABET_SIZE -this.key);
    }

    private String cipher(String text, int s){
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (Character.isUpperCase(currentChar)) {
                char ch = (char) (((int) currentChar + s - 65) % Cipher.ALPHABET_SIZE + 65);
                encryptedText.append(ch);
            } else if (Character.isLowerCase(currentChar)){
                char ch = (char) (((int) currentChar + s - 97) % Cipher.ALPHABET_SIZE + 97);
                encryptedText.append(ch);
            } else {
                encryptedText.append(currentChar);
            }
        }
        return encryptedText.toString();
    }
}

package com.company;

import com.company.BlockCiphers.DES;
import com.company.StreamCiphers.RC4;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        testStreamCipher();
        testBlockCipher();
    }

    private static void testStreamCipher() {
        RC4 r = new RC4("Wiki");

        byte[] encrypted = r.encrypt("pedia");
        String byteArrayToString = new String(encrypted);
        System.out.println("Encrypted: " + Arrays.toString(encrypted) + " | " + byteArrayToString);

        String decrypted = r.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted + "\n");


        RC4 r2 = new RC4("Key");

        byte[] encrypted2 = r2.encrypt("Plaintext");
        String byteArrayToString2 = new String(encrypted2);
        System.out.println("Encrypted: " + Arrays.toString(encrypted2) + " | " + byteArrayToString2);

        String decrypted2 = r2.decrypt(encrypted2);
        System.out.println("Decrypted: " + decrypted2 + "\n");


        RC4 r3 = new RC4("Secret");

        byte[] encrypted3 = r3.encrypt("Attack at dawn");
        String byteArrayToString3 = new String(encrypted3);
        System.out.println("Encrypted: " + Arrays.toString(encrypted3) + " | " + byteArrayToString3);

        String decrypted3 = r3.decrypt(encrypted3);
        System.out.println("Decrypted: " + decrypted3 + "\n");
    }

    private static void testBlockCipher() {
        String text = "123456ABCD132536";
        String key = "AABB09182736CCDD";

        DES cipher = new DES(key);
        System.out.println("Encryption:\n");

        text = cipher.encrypt(text);

        System.out.println("\nCipher Text: " + text.toUpperCase() + "\n");
        System.out.println("Decryption\n");

        text = cipher.decrypt(text);
        System.out.println("\nPlain Text: " + text.toUpperCase());
    }
}

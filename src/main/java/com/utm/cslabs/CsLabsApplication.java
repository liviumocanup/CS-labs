package com.utm.cslabs;

import com.utm.cslabs.asymmetric.implementation.RSA;
import com.utm.cslabs.asymmetric.implementation.RSAKey;
import com.utm.cslabs.classic.implementations.Affine;
import com.utm.cslabs.classic.implementations.Caesar;
import com.utm.cslabs.classic.implementations.Vigenere;
import com.utm.cslabs.classic.implementations.Xor;
import com.utm.cslabs.hash.UserMessage;
import com.utm.cslabs.streamBlock.BlockCiphers.DES;
import com.utm.cslabs.streamBlock.StreamCiphers.RC4;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.util.Arrays;

@SpringBootApplication
public class CsLabsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsLabsApplication.class, args);

        /*
        //Lab 1 test
        System.out.println("=====Classic Ciphers Test=====");
        classicTest();
        System.out.println("------------------------------\n\n");

        //Lab 2 test
        System.out.println("=====Asymmetric Ciphers Test=====");
        asymmetricTest();
        System.out.println("------------------------------\n\n");

        //Lab 3 test
        System.out.println("=====Stream Ciphers Test=====");
        testStreamCipher();
        System.out.println("------------------------------\n\n");
        System.out.println("=====Block Ciphers Test=====");
        testBlockCipher();
        System.out.println("------------------------------\n\n");

        //Lab 4 test (user addition test were made with JUnit and Mockito)
        System.out.println("=====Hashing Message Test=====");
        testHash();
        System.out.println("------------------------------\n\n");
        */
    }

    private static void classicTest() {
        String message = "The quick brown fox jumps over 13 lazy dogs.";

        int caesarShift = 3;
        Caesar caesar = new Caesar(caesarShift);
        String encryptedCaesar = caesar.encrypt(message);
        System.out.println("Caesar's Cipher encrypted: " + encryptedCaesar + "  with shift of " + caesarShift);
        System.out.println("Caesar's Cipher decrypted: " + caesar.decrypt(encryptedCaesar));

        String vigenereKey = "Science";
        Vigenere vigenere = new Vigenere(vigenereKey);
        String encryptedVigenere = vigenere.encrypt(message);
        System.out.println("\nVigenere Cipher encrypted: " + encryptedVigenere + "  with key: " + vigenereKey);
        System.out.println("Vigenere Cipher decrypted: " + vigenere.decrypt(encryptedVigenere));

        char xorKey = 'P';
        Xor xor = new Xor(xorKey);
        String encryptedXor = xor.encrypt(message);
        System.out.println("\nXor Cipher encrypted: " + encryptedXor + "  with key: " + xorKey);
        System.out.println("Xor Cipher decrypted: " + xor.decrypt(encryptedXor));

        int a = 3, b = 1;
        Affine affine = new Affine(a, b);
        String encryptedAffine = affine.encrypt(message);
        System.out.println("\nAffine Cipher encrypted: " + encryptedAffine + "  with a and b: " + a + ", " + b);
        System.out.println("Affine Cipher decrypted: " + affine.decrypt(encryptedAffine));
    }

    private static void asymmetricTest() {
        RSA r = new RSA();

        RSAKey rsaKey = r.generateKeys(512);

        System.out.println("Public key: " + rsaKey.getPublicKey());
        System.out.println("Private key: " + rsaKey.getPrivateKey());

        byte[] encrypted = r.encrypt("Hello World", rsaKey);
        String decrypted = r.decrypt(encrypted, rsaKey);

        System.out.println("Encrypted: " + new BigInteger(encrypted));
        System.out.println("Decrypted: " + decrypted);
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

    private static void testHash() {
        String mess = "Random message here";
        String decrypted = UserMessage.writeMessage(mess);
        String decrypted2 = UserMessage.writeMessage(mess);

        System.out.println("Decrypted 1: " + decrypted +
                "\nDecrypted 2: " + decrypted2 +
                "\nAre they equal? " + decrypted2.equals(decrypted));
    }
}

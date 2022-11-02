package com.utm;

import com.utm.implementation.RSA;
import com.utm.implementation.RSAKey;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        RSA r = new RSA();

        RSAKey rsaKey = r.generateKeys(512);

        System.out.println("Public key: "+rsaKey.getPublicKey());
        System.out.println("Private key: "+rsaKey.getPrivateKey());

        byte[] encrypted = r.encrypt("Hello World", rsaKey);
        String decrypted = r.decrypt(encrypted, rsaKey);

        System.out.println("Encrypted: " + new BigInteger(encrypted));
        System.out.println("Decrypted: " + decrypted);
    }
}

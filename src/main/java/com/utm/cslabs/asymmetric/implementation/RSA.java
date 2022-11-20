package com.utm.cslabs.asymmetric.implementation;

import com.utm.cslabs.asymmetric.RSACipher;

import java.math.BigInteger;
import java.util.Random;

public class RSA implements RSACipher {
    private BigInteger p, q, n, phi, e, d;

    public RSA() {
    }

    public RSA(int numberOfBits) {
        generateKeys(numberOfBits);
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return a;
        } else {
            return gcd(b, a.mod(b));
        }
    }

    public static BigInteger[] extendedEuclidean(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO))
            return new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};

        BigInteger[] values = extendedEuclidean(b, a.mod(b));
        BigInteger d = values[0];
        BigInteger p = values[2];
        BigInteger q = values[1].subtract(a.divide(b).multiply(values[2]));

        return new BigInteger[]{d, p, q};
    }

    public static BigInteger generateE(BigInteger phi) {
        Random rand = new Random();
        BigInteger e;

        do {
            e = new BigInteger(1024, rand);
            while (e.min(phi).equals(phi)) {
                e = new BigInteger(1024, rand);
            }
        } while (!gcd(e, phi).equals(BigInteger.ONE));

        return e;
    }

    public RSAKey generateKeys(int numberOfBits) {
        p = BigInteger.probablePrime(numberOfBits, new Random());
        q = BigInteger.probablePrime(numberOfBits, new Random());
        n = p.multiply(q);
        phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = generateE(phi);
        d = extendedEuclidean(e, phi)[1];

        return new RSAKey(e, d, n);
    }

    @Override
    public byte[] encrypt(String message, RSAKey rsaKey) {
        BigInteger cipherMessage = new BigInteger(message.getBytes());
        return cipherMessage.modPow(rsaKey.getPublicKey(), rsaKey.getN()).toByteArray();
    }

    @Override
    public String decrypt(byte[] message, RSAKey rsaKey) {
        BigInteger decryptedMessage = new BigInteger(message).modPow(rsaKey.getPrivateKey(), rsaKey.getN());
        return new String(decryptedMessage.toByteArray());
    }


}

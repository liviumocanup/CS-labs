package com.utm.cslabs.asymmetric.implementation;

import java.math.BigInteger;

public class RSAKey {
    private final BigInteger publicKey;
    private final BigInteger privateKey;
    private final BigInteger n;

    public RSAKey(BigInteger publicKey, BigInteger privateKey, BigInteger n) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.n = n;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getN() {
        return n;
    }
}

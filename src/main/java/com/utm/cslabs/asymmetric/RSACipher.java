package com.utm.cslabs.asymmetric;

import com.utm.cslabs.asymmetric.implementation.RSAKey;

public interface RSACipher {
    byte[] encrypt(final String message, final RSAKey rsaKey);

    String decrypt(final byte[] message, final RSAKey rsaKey);
}
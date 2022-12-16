package com.utm.cslabs.mfa.service;

import com.utm.cslabs.asymmetric.implementation.RSA;
import com.utm.cslabs.asymmetric.implementation.RSAKey;
import com.utm.cslabs.classic.implementations.Caesar;
import com.utm.cslabs.mfa.models.response.CipherResponse;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class CipherService {
    public CipherResponse caesar(String message, int shift){
        Caesar caesar = new Caesar(shift);
        String encryptedCaesar = caesar.encrypt(message);
        return new CipherResponse(
                "Caesar's Cipher encrypted: " + encryptedCaesar + " with shift " + shift +".",
                "Caesar's Cipher decrypted: " + caesar.decrypt(encryptedCaesar)
        );
    }

    public CipherResponse asymmetric(String message){
        RSA r = new RSA();

        RSAKey rsaKey = r.generateKeys(512);

        byte[] encrypted = r.encrypt(message, rsaKey);
        String decrypted = r.decrypt(encrypted, rsaKey);

        return new CipherResponse(
                "Encrypted: " + new BigInteger(encrypted) +".",
                "Decrypted: " + decrypted
        );
    }
}

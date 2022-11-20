package com.utm.cslabs.hash;

import com.utm.cslabs.asymmetric.implementation.RSA;
import com.utm.cslabs.asymmetric.implementation.RSAKey;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserMessage {
    public static String writeMessage(String message) {
        RSA rsa = new RSA();
        String hashedMessage = BCrypt.hashpw(message, BCrypt.gensalt());
        RSAKey rsaKeyPair = rsa.generateKeys(512);

        byte[] encrypted = rsa.encrypt(hashedMessage, rsaKeyPair);
        String decrypted = rsa.decrypt(encrypted, rsaKeyPair);

        System.out.println("HashedMessage = "+hashedMessage);
        System.out.println("Decrypted = "+decrypted);
        System.out.println("Are they equal? " + hashedMessage.equals(decrypted)+"\n");
        return decrypted;
    }
}

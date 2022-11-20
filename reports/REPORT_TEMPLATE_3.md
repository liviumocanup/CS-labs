# Topic: Asymmetric Ciphers.

### Course: Cryptography & Security
### Author: Mocanu Liviu

----

## Overview
&ensp;&ensp;&ensp; Asymmetric Cryptography (a.k.a. Public-Key Cryptography)deals with the encryption of plain text when having 2 keys, one being public and the other one private. The keys form a pair and despite being different they are related.

&ensp;&ensp;&ensp; As the name implies, the public key is available to the public but the private one is available only to the authenticated recipients. 

&ensp;&ensp;&ensp; A popular use case of the asymmetric encryption is in SSL/TLS certificates along side symmetric encryption mechanisms. It is necessary to use both types of encryption because asymmetric ciphers are computationally expensive, so these are usually used for the communication initiation and key exchange, or sometimes called handshake. The messages after that are encrypted with symmetric ciphers.


## Examples
1. RSA
2. Diffie-Helman
3. ECC
4. El Gamal
5. DSA


## Objectives:
1. Get familiar with the asymmetric cryptography mechanisms.

2. Implement an example of an asymmetric cipher.

3. As in the previous task, please use a client class or test classes to showcase the execution of your programs.

   
## Implementation Description:

### RSA

RSA is an asymmetric cipher that uses the fact that it is easy to find the prime factors of a large number, but it is difficult to find the prime factors of a product of two large prime numbers. It uses two keys, one public and one private. The public key is used to encrypt the message and the private key is used to decrypt the message.
**Key generation steps:**
1. Generate two large prime numbers `p` and `q`.
```go
    public RSAKey generateKeys(int numberOfBits){
        p = BigInteger.probablePrime(numberOfBits, new Random());
        q = BigInteger.probablePrime(numberOfBits, new Random());
        ...
    }
```

2. Compute `n = p * q`.
```go
    public RSAKey generateKeys(int numberOfBits){
        ...
        n = p.multiply(q);
        ...
    }
```

3. Compute `phi(n) = (p - 1) * (q - 1)`.
```go
    public RSAKey generateKeys(int numberOfBits){
        ...
        phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        ...
    }
```

4. Choose an integer `e` such that `1 < e < phi(n)` and `gcd(e, phi(n)) = 1`. This is the public key.
```go
    public RSAKey generateKeys(int numberOfBits){
        ...
        e = generateE(phi);
        ...
    }
    
    public static BigInteger generateE(BigInteger phi) {
        ...
        do {
            e = new BigInteger(1024, rand);
            while (e.min(phi).equals(phi)) {
                e = new BigInteger(1024, rand);
            }
        } while (!gcd(e, phi).equals(BigInteger.ONE));
        ...
    }
```

5. Compute `d` such that `d * e = 1 mod phi(n)`. This is the private key.
```go
    public RSAKey generateKeys(int numberOfBits){
        ...
        d = extendedEuclidean(e, phi)[1];
        ...
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
```

**Encryption:**

To compute `encrypted` from plain text `message` we use: `message ^ e mod n`. (when generating keys, rsaKey.publicKey got set as e)
```go
    public byte[] encrypt(String message, RSAKey rsaKey) {
        BigInteger cipherMessage = new BigInteger(message.getBytes());
        return cipherMessage.modPow(rsaKey.getPublicKey(), rsaKey.getN()).toByteArray();
    }
```

**Decryption:**

To compute `decrypted` from encrypted text `message` we use: `decryptedMessage ^ d mod n`. (when generating keys, rsaKey.privateKey got set as d)
```go
    public String decrypt(byte[] message, RSAKey rsaKey) {
        BigInteger decryptedMessage = new BigInteger(message).modPow(rsaKey.getPrivateKey(), rsaKey.getN());
        return new String(decryptedMessage.toByteArray());
    }
```

## Conclusion:

After completing this lab assignment, I now understand how to use and implement an asymmetric cipher. An extremely common asymmetric cipher used for both data encryption and decryption is called RSA. Although it might be slow and is not ideal for handling big volumes of data, it is quite secure.
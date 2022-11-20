# Topic: Hash functions and Digital Signatures.

### Course: Cryptography & Security
### Author: Mocanu Liviu

----

## Overview
&ensp;&ensp;&ensp; Hashing is a technique used to compute a new representation of an existing value, message or any piece of text. The new representation is also commonly called a digest of the initial text, and it is a one way function meaning that it should be impossible to retrieve the initial content from the digest.

&ensp;&ensp;&ensp; Such a technique has the following usages:
* Offering confidentiality when storing passwords,
* Checking for integrity for some downloaded files or content,
* Creation of digital signatures, which provides integrity and non-repudiation.

&ensp;&ensp;&ensp; In order to create digital signatures, the initial message or text needs to be hashed to get the digest. After that, the digest is to be encrypted using a public key encryption cipher. Having this, the obtained digital signature can be decrypted with the public key and the hash can be compared with an additional hash computed from the received message to check the integrity of it.


## Examples
1. Argon2
2. BCrypt
3. MD5 (Deprecated due to collisions)
4. RipeMD
5. SHA256 (And other variations of SHA)
6. Whirlpool


## Objectives:
1. Get familiar with the hashing techniques/algorithms.
2. Use an appropriate hashing algorithms to store passwords in a local DB.
    1. You can use already implemented algortihms from libraries provided for your language.
    2. The DB choise is up to you, but it can be something simple, like an in memory one.
3. Use an asymmetric cipher to implement a digital signature process for a user message.
    1. Take the user input message.
    2. Preprocess the message, if needed.
    3. Get a digest of it via hashing.
    4. Encrypt it with the chosen cipher.
    5. Perform a digital signature check by comparing the hash of the message with the decrypted one.


## Implementation Description:

&ensp;&ensp;&ensp; For this laboratory work I used BCrypt as the hashing algorithm and H2 as the in-memory-database as well as the previously implemented RSA as the asymmetric cipher for the digital signature. The laboratory implementation is separated in two classes `PersistUser` and `UserMessage`. `PersistUser` is making use of H2 in-memory-database that stores the users.

&ensp;&ensp;&ensp; For the first objective, `PeristUser` has the method for registering a new user and persisting the database with the user possessing an already hashed password in the database.

```java
public String addUser() {
     String password = "VerySafePassword";
     String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

     User savedUser = usersRepository.save(
        new User().setPassword(hashedPassword)
     );

     System.out.println(("Hashed password stored: "+savedUser.getPassword()));
     return savedUser.getPassword();
     }
}
```
&ensp;&ensp;&ensp; For the second objective, the `UserMessage` has the method for signing a message and verifying the signature. The hashing algorithm used is same as in `PersistUser`, **BCrypt**. It will hash the message, encrypt it with the private key and return the signature. Then we decrypt the signature with the public key and compare the two hashes.

```java
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
```

Since we're using BCrypt with salt, even two identical messages/passwords are unlikely to have the same hash.

## Conclusion:

&ensp;&ensp;&ensp; Hashing is a very helpful technology that may be used to produce digital signatures, store passwords, and verify data integrity. Large files can be checked for integrity using the quick hashing techniques. The technique of a digital signature is highly helpful for integrity and non-repudiation. A message's authenticity can be confirmed and any alterations to it can be checked using the digital signature.
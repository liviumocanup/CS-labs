# Topic: Symmetric Ciphers. Stream Ciphers. Block Ciphers.

### Course: Cryptography & Security

### Author: Mocanu Liviu

---

## Overview

&ensp;&ensp;&ensp; Symmetric Cryptography deals with the encryption of plain text when having only one encryption key which needs to remain private. Based on the way the plain text is processed/encrypted there are 2 types of ciphers:

- Stream ciphers:
  - The encryption is done one byte at a time.
  - Stream ciphers use confusion to hide the plain text.
  - Make use of substitution techniques to modify the plain text.
  - The implementation is fairly complex.
  - The execution is fast.
- Block ciphers:
  - The encryption is done one block of plain text at a time.
  - Block ciphers use confusion and diffusion to hide the plain text.
  - Make use of transposition techniques to modify the plain text.
  - The implementation is simpler relative to the stream ciphers.
  - The execution is slow compared to the stream ciphers.

## Objectives

1. Get familiar with the symmetric cryptography, stream and block ciphers.

2. Implement an example of a stream cipher RC4.

3. Implement an example of a block cipher DES.

## Implementation description

### Stream cipher: RC4

RC4 generates a pseudorandom stream of bits (a keystream). As with any stream cipher, these can be used for encryption by combining it with the plaintext using bitwise exclusive or; decryption is performed the same way (since exclusive or with given data is an involution). This is similar to the one-time pad, except that generated pseudorandom bits, rather than a prepared stream, are used.

To generate the keystream, the cipher makes use of a secret internal state which consists of two parts:

* A permutation of all 256 possible bytes (denoted "S" below).
* Two 8-bit index-pointers (denoted "i" and "j").

The permutation is initialized with a variable-length key, typically between 40 and 2048 bits, using the key-scheduling algorithm (KSA). Once this has been completed, the stream of bits is generated using the pseudo-random generation algorithm (PRGA).

####Key-scheduling algorithm (KSA)
The key-scheduling algorithm is used to initialize the permutation in the array "S". "keylength" is defined as the number of bytes in the key and can be in the range 1 ≤ keylength ≤ 256, typically between 5 and 16, corresponding to a key length of 40–128 bits. First, the array "S" is initialized to the identity permutation. S is then processed for 256 iterations in a similar way to the main PRGA, but also mixes in bytes of the key at the same time.

    // Key-Scheduling Algorithm
    private void ksa() {
        int keyLength = key.length;

        for (int i = 0; i < BYTE_VALUES; i++) {
            S[i] = (byte) i;
        }

        int j = 0;
        for (int i = 0; i < BYTE_VALUES; i++) {
            j = (j + unsignedToBytes(S[i]) + unsignedToBytes(key[i % keyLength])) % BYTE_VALUES;
            byte tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;

        }
    }

####Pseudo-random generation algorithm (PRGA)
For as many iterations as are needed, the PRGA modifies the state and outputs a byte of the keystream. In each iteration, the PRGA:

* increments i;
* looks up the ith element of S, S[i], and adds that to j;
* exchanges the values of S[i] and S[j], then uses the sum S[i] + S[j] (modulo 256) as an index to fetch a third element of S (the keystream value K below);
* then bitwise exclusive ORed (XORed) with the next byte of the message to produce the next byte of either ciphertext or plaintext.

Each element of S is swapped with another element at least once every 256 iterations.

    //Pseudo-random generation algorithm
    private byte prga() {
        x = (x + 1) % BYTE_VALUES;
        y = (y + unsignedToBytes(S[x])) % BYTE_VALUES;

        byte temp = S[x];
        S[x] = S[y];
        S[y] = temp;

        return S[(unsignedToBytes(S[x]) + unsignedToBytes(S[y])) % BYTE_VALUES];
    }


### Block cipher: DES

DES is a block cipher and encrypts data in blocks of size of 64 bits each, which means 64 bits of plain text go as the input to DES, which produces 64 bits of ciphertext. The same algorithm and key are used for encryption and decryption, with minor differences. The key length is 56 bits.
Before the DES process even starts, every 8th bit of the key is discarded to produce a 56-bit key. That is bit positions 8, 16, 24, 32, 40, 48, 56, and 64 are discarded.

Thus, the discarding of every 8th bit of the key produces a 56-bit key from the original 64-bit key.
DES is based on the two fundamental attributes of cryptography: substitution (also called confusion) and transposition (also called diffusion). DES consists of 16 steps, each of which is called a round. Each round performs the steps of substitution and transposition.

* In the first step, the 64-bit plain text block is handed over to an initial Permutation (IP) function.
* The initial permutation is performed on plain text.


    private String cipher(String plainText, String key, char e) {
      int i;
      // get round keys
      String[] keys = getKeys(key);
    
      // initial permutation
      plainText = permutation(INITIAL_PERMUTATION_TABLE, plainText);

      ...
    }

    private String permutation(int[] sequence, String input) {
        StringBuilder output = new StringBuilder();
        input = hextoBin(input);

        for (int j : sequence) output.append(input.charAt(j - 1));

        output = new StringBuilder(binToHex(output.toString()));
        return output.toString();
    }
* Next, the initial permutation (IP) produces two halves of the permuted block; saying Left Plain Text (LPT) and Right Plain Text (RPT).
* Now each LPT and RPT go through 16 rounds of the encryption process.


    private String cipher(String plainText, String key, char e) {
        ...

        for (i = 0; i < 16; i++) {
            plainText = round(plainText, keys[i], i);
        }

        ...
    }

    private String round(String input, String key, int num) {
        // fk
        String left = input.substring(0, 8);
        String temp = input.substring(8, 16);
        String right = temp;

        // Expansion permutation
        temp = permutation(EXPANSION_DBOX_TABLE, temp);

        // xor temp and round key
        temp = xor(temp, key);

        // lookup in s-box table
        temp = sBox(temp);

        // Straight D-box
        temp = permutation(STRAIGHT_PERMUTATION_TABLE, temp);

        // xor
        left = xor(left, temp);

        // swapper
        return right + left;
    }

* In the end, LPT and RPT are rejoined and a Final Permutation (FP) is performed on the combined block
* The result of this process produces 64-bit ciphertext.


    private String cipher(String plainText, String key, char e) {

        ...
  
        // 32-bit swap
        plainText = plainText.substring(8, 16) + plainText.substring(0, 8);
  
        // final permutation
        plainText = permutation(INVERSE_INITIAL_PERMUTATION_TABLE, plainText);
        return plainText;
    }

## Conclusions

This laboratory work gives a better understanding of the complexity of the symmetric cryptography as well as stream and block ciphers.
With the newly acquired knowledge, one could easily spot the differences between the two types of cyphers, mainly the fact that stream ciphers encrypt one byte at a time, whereas the block ciphers encrypt one block of plain text at a time.
Moreover, the stream ciphers are fast to execute and have a somewhat sophisticated implementation whilst the execution of block ciphers is slower due to the fact that their implementation is simpler.
package com.utm.cslabs.streamBlock.BlockCiphers;

import static com.utm.cslabs.streamBlock.BlockCiphers.Constants.*;

public class DES implements BlockCipher {
    private final String globalKey;

    public DES(String key) {
        this.globalKey = key;
    }

    @Override
    public String encrypt(String plainText) {
        return cipher(plainText, globalKey, 'e');
    }

    @Override
    public String decrypt(String plainText) {
        return cipher(plainText, globalKey, 'd');
    }

    private String cipher(String plainText, String key, char e) {
        int i;
        // get round keys
        String[] keys = getKeys(key);

        // initial permutation
        plainText = permutation(INITIAL_PERMUTATION_TABLE, plainText);
        System.out.println("After initial permutation: " + plainText.toUpperCase());
        System.out.println("After splitting: L0=" + plainText.substring(0, 8).toUpperCase() + " R0=" + plainText.substring(8, 16).toUpperCase() + "\n");

        // 16-rounds
        if (e == 'd') {
            for (i = 15; i > -1; i--) {
                plainText = round(plainText, keys[i], 15 - i);
            }
        } else {
            for (i = 0; i < 16; i++) {
                plainText = round(plainText, keys[i], i);
            }
        }

        // 32-bit swap
        plainText = plainText.substring(8, 16) + plainText.substring(0, 8);

        // final permutation
        plainText = permutation(INVERSE_INITIAL_PERMUTATION_TABLE, plainText);
        return plainText;
    }

    private String hextoBin(String input) {
        int n = input.length() * 4;
        StringBuilder inputBuilder = new StringBuilder(Long.toBinaryString(Long.parseUnsignedLong(input, 16)));

        while (inputBuilder.length() < n)
            inputBuilder.insert(0, "0");

        input = inputBuilder.toString();
        return input;
    }

    private String binToHex(String input) {
        int n = input.length() / 4;
        StringBuilder inputBuilder = new StringBuilder(Long.toHexString(Long.parseUnsignedLong(input, 2)));

        while (inputBuilder.length() < n)
            inputBuilder.insert(0, "0");

        input = inputBuilder.toString();
        return input;
    }

    // permutate input hexadecimal according to specified sequence
    private String permutation(int[] sequence, String input) {
        StringBuilder output = new StringBuilder();
        input = hextoBin(input);

        for (int j : sequence) output.append(input.charAt(j - 1));

        output = new StringBuilder(binToHex(output.toString()));
        return output.toString();
    }

    // xor 2 hexadecimal strings
    private String xor(String a, String b) {
        // hexadecimal to decimal(base 10)
        long t_a = Long.parseUnsignedLong(a, 16);
        // hexadecimal to decimal(base 10)
        long t_b = Long.parseUnsignedLong(b, 16);
        // xor
        t_a = t_a ^ t_b;
        // decimal to hexadecimal
        // prepend 0's to maintain length
        StringBuilder aBuilder = new StringBuilder(Long.toHexString(t_a));

        while (aBuilder.length() < b.length())
            aBuilder.insert(0, "0");

        a = aBuilder.toString();
        return a;
    }

    // left Circular Shifting bits
    private String leftCircularShift(String input, int numBits) {
        int n = input.length() * 4;
        int[] perm = new int[n];

        for (int i = 0; i < n - 1; i++)
            perm[i] = (i + 2);

        perm[n - 1] = 1;

        while (numBits-- > 0)
            input = permutation(perm, input);
        return input;
    }

    // preparing 16 keys for 16 rounds
    private String[] getKeys(String key) {
        String[] keys = new String[16];
        // first key permutation
        key = permutation(FIRST_KEY_PERMUTATION_TABLE, key);

        for (int i = 0; i < 16; i++) {
            key = leftCircularShift(key.substring(0, 7), SHIFT_BITS[i]) + leftCircularShift(key.substring(7, 14), SHIFT_BITS[i]);
            // second key permutation
            keys[i] = permutation(SECOND_KEY_PERMUTATION_TABLE, key);
        }

        return keys;
    }

    // s-box lookup
    private String sBox(String input) {
        StringBuilder output = new StringBuilder();
        input = hextoBin(input);
        for (int i = 0; i < 48; i += 6) {
            String temp = input.substring(i, i + 6);
            int num = i / 6;
            int row = Integer.parseInt(temp.charAt(0) + "" + temp.charAt(5), 2);
            int col = Integer.parseInt(temp.substring(1, 5), 2);
            output.append(Integer.toHexString(S_BOX[num][row][col]));
        }
        return output.toString();
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
        System.out.println("Round " + (num + 1) + " " + right.toUpperCase() + " " + left.toUpperCase() + " " + key.toUpperCase());

        // swapper
        return right + left;
    }
}
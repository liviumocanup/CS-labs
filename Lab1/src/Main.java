import implementations.Affine;
import implementations.Caesar;
import implementations.Vigenere;
import implementations.Xor;

public class Main {
    public static void main(String[] args) {
        String message = "The quick brown fox jumps over 13 lazy dogs.";

        int caesarShift = 3;
        Caesar caesar = new Caesar(caesarShift);
        String encryptedCaesar = caesar.encrypt(message);
        System.out.println("Caesar's Cipher encrypted: "+ encryptedCaesar +"  with shift of "+ caesarShift);
        System.out.println("Caesar's Cipher decrypted: "+ caesar.decrypt(encryptedCaesar));

        String vigenereKey = "Science";
        Vigenere vigenere = new Vigenere(vigenereKey);
        String encryptedVigenere = vigenere.encrypt(message);
        System.out.println("\nVigenere Cipher encrypted: "+ encryptedVigenere + "  with key: "+ vigenereKey);
        System.out.println("Vigenere Cipher decrypted: "+ vigenere.decrypt(encryptedVigenere));

        char xorKey = 'P';
        Xor xor = new Xor(xorKey);
        String encryptedXor = xor.encrypt(message);
        System.out.println("\nXor Cipher encrypted: "+ encryptedXor + "  with key: "+ xorKey);
        System.out.println("Xor Cipher decrypted: "+ xor.decrypt(encryptedXor));

        int a=3, b=1;
        Affine affine = new Affine(a, b);
        String encryptedAffine = affine.encrypt(message);
        System.out.println("\nAffine Cipher encrypted: "+ encryptedAffine + "  with a and b: "+ a +", "+b);
        System.out.println("Affine Cipher decrypted: "+ affine.decrypt(encryptedAffine));
    }
}
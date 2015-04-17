package com.PBox;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.util.Scanner;

public class PBox {

    private int[] _key;
    private int[] _reverseKey;

    public PBox(int[] key) {

        this._key = key;
        this._prepareReverseKey();
    }

    private void _prepareReverseKey() {

        this._reverseKey = new int[this._key.length];
        for (int i = 0; i < this._key.length; i++) {
            this._reverseKey[this._key[i]] = i;
        }
    }

    private int[] _toBinary(int number) {

        int length = this._key.length;
        int bits[] = new int[length];
        int index = length - 1;

        while (number != 0) {
            bits[index--] = number % 2;
            number = number / 2;
        }

        return bits;
    }

    private int _toDecimal(int bits[]) {

        int number = 0;
        int power = 1;

        for (int i = bits.length-1; i >= 0; i--, power *= 2) {
            number += (bits[i] * power);
        }

        return number;
    }

    public String encrypt(String text) {

        String encryptedText = "";
        int plainCharBits[];
        int encryptCharBits[];
        for (int i = 0; i < text.length(); i++) {

            plainCharBits = this._toBinary((int)text.charAt(i));
            encryptCharBits = new int[this._key.length];

            for (int j=0; j<this._key.length; j++) {
                encryptCharBits[j] = plainCharBits[this._key[j]];
            }

            encryptedText += (char)this._toDecimal(encryptCharBits);
        }

        return encryptedText;
    }

    public String decrypt(String text) {

        String decryptedText = "";
        int encryptCharBits[];
        int plainCharBits[];
        for (int i = 0; i < text.length(); i++) {

            encryptCharBits = this._toBinary((int)text.charAt(i));
            plainCharBits = new int[this._key.length];

            for (int j=0; j<this._key.length; j++) {
                plainCharBits[j] = encryptCharBits[this._reverseKey[j]];
            }

            decryptedText += (char)this._toDecimal(plainCharBits);
        }

        return decryptedText;
    }

    public static void main(String[] args) {

        if (args.length < 1) {

            System.out.println("File to read the P-box settings must be provided as first parameter");
            return;
        }

        String fileName = args[0];
        char pBoxStr[];

        try {

            Scanner file = new Scanner(new File(fileName));
            pBoxStr = file.nextLine().toCharArray();
            file.close();

        } catch (Exception e) {

            System.err.println(e);
            return;
        }

        int length = pBoxStr.length;
        int start;

        for (start = 2; start < length; start *= 2);
        if (start != length) {

            System.out.println("Number of elements in P-Box must be a power of 2");
            return;
        }

        int key[] = new int[length];

        for (int i = 0; i < pBoxStr.length; i++) {
            key[i] = pBoxStr[i] - '0';
        }

        PBox pBox = new PBox(key);

        System.out.print("Enter the plain text : ");
        Scanner input = new Scanner(System.in);
        String plainText = input.nextLine();
        String cipherText = pBox.encrypt(plainText);
        String decryptText = pBox.decrypt(cipherText);

        System.out.println("Plain Text = " + plainText);
        System.out.println("Cipher Text = " + cipherText);
        System.out.println("Plain Text (after Decryption) = " + decryptText);
    }
}

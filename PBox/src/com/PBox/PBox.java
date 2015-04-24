package com.PBox;

public class PBox {

    private int[] key;
    private int[] reverseKey;

    public PBox(int[] key) {

        this.key = key;
        this.prepareReverseKey();
    }

    private void prepareReverseKey() {

        this.reverseKey = new int[this.key.length];
        for (int i = 0; i < this.key.length; i++) {
            this.reverseKey[this.key[i]] = i;
        }
    }

    private int[] _toBinary(int number) {

        int length = this.key.length, index = length - 1;
        int bits[] = new int[length];

        while (number != 0) {
            bits[index--] = number % 2;
            number = number / 2;
        }

        return bits;
    }

    private int _toDecimal(int bits[]) {

        int number = 0, power = 1;

        for (int i = bits.length-1; i >= 0; i--, power *= 2) {
            number += (bits[i] * power);
        }

        return number;
    }

    public String encrypt(String text) {

        String encryptedText = "";
        int plainCharBits[], encryptCharBits[];

        for (int i = 0; i < text.length(); i++) {

            plainCharBits = this._toBinary((int)text.charAt(i));
            encryptCharBits = new int[this.key.length];

            for (int j=0; j<this.key.length; j++) {
                encryptCharBits[j] = plainCharBits[this.key[j]];
            }

            encryptedText += (char)this._toDecimal(encryptCharBits);
        }

        return encryptedText;
    }

    public String decrypt(String text) {

        String decryptedText = "";
        int encryptCharBits[], plainCharBits[];

        for (int i = 0; i < text.length(); i++) {

            encryptCharBits = this._toBinary((int)text.charAt(i));
            plainCharBits = new int[this.key.length];

            for (int j=0; j<this.key.length; j++) {
                plainCharBits[j] = encryptCharBits[this.reverseKey[j]];
            }

            decryptedText += (char)this._toDecimal(plainCharBits);
        }

        return decryptedText;
    }
}
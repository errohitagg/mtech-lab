package com.SBox;

public class SBox {

    private int key[][];
    private int reverseKey[][];

    public SBox(int[] key) {

        int i, j, k, x, y;

        this.key = new int[4][4];
        this.reverseKey = new int[4][4];

        for (k= 0, x = 0, y = 0; k < key.length; k++, y++) {
            i = key[k] & 3;
            j = (key[k] & (3 << 2)) >> 2;
            this.reverseKey[i][j] = (y * 4) + x;
            this.key[x][y] = key[k];

            if (y == 3) {
                x++;
                y = -1;
            }
        }
    }

    public String encrypt(String text) {

        String encryptedText = "";
        int plainCharBits, cipherCharBits, i, j, k;

        for (k = 0; k < text.length(); k++) {

            plainCharBits = (int) text.charAt(k);
            i = plainCharBits & 3;
            j = (plainCharBits & ((3 << 6) >> 4)) >> 2;
            cipherCharBits = this.key[i][j];

            i = (plainCharBits & ((3 << 6) >> 2)) >> 4;
            j = (plainCharBits & (3 << 6)) >> 6;
            cipherCharBits = (this.key[i][j] << 4) | cipherCharBits;

            encryptedText += (char)cipherCharBits;
        }

        return encryptedText;
    }

    public String decrypt(String text) {

        String decryptedText = "";
        int cipherCharBits, plainCharBits, i, j, k;

        for (k = 0; k < text.length(); k++) {

            cipherCharBits = (int) text.charAt(k);
            i = cipherCharBits & 3;
            j = (cipherCharBits & ((3 << 6) >> 4)) >> 2;
            plainCharBits = this.reverseKey[i][j];

            i = (cipherCharBits & ((3 << 6) >> 2)) >> 4;
            j = (cipherCharBits & (3 << 6)) >> 6;
            plainCharBits = (this.reverseKey[i][j] << 4) | plainCharBits;

            decryptedText += (char)plainCharBits;
        }

        return decryptedText;
    }

    public static void main(String[] args) {

        SBox obj = new SBox(new int[]{10, 11, 12, 13, 15, 14, 2, 4, 6 , 8, 1, 3, 5, 7, 9, 0});
        String result = obj.encrypt("Hello");
        System.out.println(result);
        result = obj.decrypt(result);
        System.out.println(result);
    }
}

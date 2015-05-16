package com.PBox;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("File to read the P-box settings must be provided as first parameter");
            return;
        }

        String fileName = args[0];
        String pBoxStr[];

        try {
            Scanner file = new Scanner(new File(fileName));
            pBoxStr = file.nextLine().split(" ");
            file.close();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }

        if (pBoxStr.length != 8) {
            System.out.println("Number of elements in P-Box must be 8");
            return;
        }

        int key[] = new int[pBoxStr.length];
        for (int i = 0; i < pBoxStr.length; i++) {
            key[i] = Integer.parseInt(pBoxStr[i]);
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
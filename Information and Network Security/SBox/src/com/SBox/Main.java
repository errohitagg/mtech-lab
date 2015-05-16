package com.SBox;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("File to read the S-box settings must be provided as first parameter");
            return;
        }

        String fileName = args[0];
        String sBoxStr[];

        try {
            Scanner file = new Scanner(new File(fileName));
            sBoxStr = file.nextLine().split(" ");
            file.close();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }

        int key[] = new int[sBoxStr.length];
        for (int i = 0; i < sBoxStr.length; i++) {
            key[i] = Integer.parseInt(sBoxStr[i]);
        }

        SBox sBox = new SBox(key);

        System.out.print("Enter the plain text : ");
        Scanner input = new Scanner(System.in);
        String plainText = input.nextLine();
        String cipherText = sBox.encrypt(plainText);
        String decryptText = sBox.decrypt(cipherText);

        System.out.println("Plain Text = " + plainText);
        System.out.println("Cipher Text = " + cipherText);
        System.out.println("Plain Text (after Decryption) = " + decryptText);
    }
}
package com.rsacrypt;

import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        RSA rsa = new RSA(new MyBigInteger("1000000000000000000001"));

        System.out.print("Enter the plain text : ");
        Scanner input = new Scanner(System.in);
        String plainText = input.nextLine();
        String cipherText = rsa.encrypt(plainText);
        String decryptText = rsa.decrypt(cipherText);

        System.out.println("\nPlain Text = " + plainText);
        System.out.println("Cipher Text = " + cipherText);
        System.out.println("Plain Text (after Decryption) = " + decryptText);
    }
}
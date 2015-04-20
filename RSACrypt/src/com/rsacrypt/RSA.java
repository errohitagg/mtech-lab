package com.rsacrypt;

import java.util.Random;

public class RSA {

    private String startNumber = "1";

    public RSA() {
    }

    public MyBigInteger generatePrime(int n) {

        MyBigInteger prime = new MyBigInteger(this.startNumber);
        Random random_number = new Random();
        int i = 0, increment;

        do {

            do {
                increment = random_number.nextInt(100);
            } while (increment % 2 == 1);

            prime = prime.add(increment);
            if (prime.isPrime()) {
                i++;
            }

        } while(i <= n);

        return prime;
    }

    public MyBigInteger generatePrime() {

        return this.generatePrime(1);
    }

    public String encrypt(String plainText) {

        return "";
    }

    public String decrypt(String cipherText) {

        return "";
    }

    public static void main(String[] args) {
	    // write your code here

        RSA algo = new RSA();
        MyBigInteger p = new MyBigInteger("61");//algo.generatePrime(5);
        algo.startNumber = p.toString();
        MyBigInteger q = new MyBigInteger("53");//algo.generatePrime(10);
        MyBigInteger n = p.multiply(q);
        MyBigInteger phiN = p.subtract(1).multiply(q.subtract(1));
        MyBigInteger e = new MyBigInteger("17");
        MyBigInteger d = e.inverse_modulus(phiN);

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("n = " + n);
        System.out.println("phiN = " + phiN);
        System.out.println("gcd(e, phiN) == 1 (mod phiN) = " + phiN.isCoPrime(e));
        System.out.println("e = " + e);
        System.out.println("d = " + d);

    }
}

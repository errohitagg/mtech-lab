package com.rsacrypt;

import java.util.Random;

public class RSA {

    private String startNumber = "1";

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

    public static void main(String[] args) {
	    // write your code here

        RSA algo = new RSA();
        System.out.println(algo.generatePrime(10));
        System.out.println(algo.generatePrime(20));
        System.out.println(algo.generatePrime(40));
    }
}

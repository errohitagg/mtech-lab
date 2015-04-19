package com.rsacrypt;

public class RSA {

    private static final String START_NUMBER = "95647806479275528135733781266203904794419563064407";

    public BigInteger generatePrime(int n) {

        BigInteger prime = new BigInteger(START_NUMBER);
        int i = 0;

        do {

            prime.add(2);
            if (prime.isPrime()) {
                i++;
            }

        } while(i <= n);

        return prime;
    }

    public BigInteger generatePrime() {

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

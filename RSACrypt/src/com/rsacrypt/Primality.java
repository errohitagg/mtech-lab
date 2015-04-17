package com.rsacrypt;

public class Primality {

    private boolean divisibleBy3(BigInteger number) {

        int sum = 0, i, j, num;
        int numbers[] = number.numbers();

        for (i = 0; i < number.size(); i++) {

            num = numbers[i];
            while (num != 0) {
                sum += num % 10;
                num = num / 10;
            }
        }

        if (sum % 3 == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean divisibleBy5(BigInteger number) {

        int size = number.size();
        int numbers[] = number.numbers();

        if (numbers[size-1] % 5 == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean divisibleByN(BigInteger number, int n, int val, boolean add) {

        BigInteger final_number = new BigInteger(number);
        String str_final_number;
        int curr_number;

        while (final_number.size() > 1) {

            str_final_number = final_number.toString();
            curr_number = Character.digit(str_final_number.charAt(str_final_number.length() - 1), 10);
            curr_number *= val;
            final_number = new BigInteger(str_final_number.substring(0, str_final_number.length() - 2));
            if (add) {
                final_number.add(curr_number);
            } else {
                final_number.subtract(curr_number);
            }
        }

        curr_number = final_number.numbers()[0];
        if (curr_number % n == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean divisibleBy7(BigInteger number) {

        return this.divisibleByN(number, 7, 2, false);
    }

    private boolean divisibleBy11(BigInteger number) {

        return this.divisibleByN(number, 11, 1, false);
    }

    private boolean divisibleBy13(BigInteger number) {

        return this.divisibleByN(number, 13, 4, true);
    }

    private boolean divisibleBy17(BigInteger number) {

        return this.divisibleByN(number, 17, 5, false);
    }

    private boolean divisibleBy19(BigInteger number) {

        return this.divisibleByN(number, 19, 2, true);
    }

    private boolean divisibleBy23(BigInteger number) {

        return this.divisibleByN(number, 23, 7, true);
    }

    private boolean divisibleBy29(BigInteger number) {

        return this.divisibleByN(number, 29, 3, true);
    }

    private boolean divisibleBy31(BigInteger number) {

        return this.divisibleByN(number, 31, 3, false);
    }

    private boolean divisibleBy37(BigInteger number) {

        return this.divisibleByN(number, 37, 11, false);
    }

    private boolean divisibleBy41(BigInteger number) {

        return this.divisibleByN(number, 41, 4, false);
    }

    private boolean divisibleBy43(BigInteger number) {

        return this.divisibleByN(number, 43, 13, true);
    }

    private boolean divisibleBy47(BigInteger number) {

        return this.divisibleByN(number, 47, 14, false);
    }

    public boolean isPrime(BigInteger number) {

        if (this.divisibleBy3(number)) {
            return false;
        }

        if (this.divisibleBy5(number)) {
            return false;
        }

        if (this.divisibleBy7(number)) {
            return false;
        }

        if (this.divisibleBy11(number)) {
            return false;
        }

        if (this.divisibleBy13(number)) {
            return false;
        }

        if (this.divisibleBy17(number)) {
            return false;
        }

        if (this.divisibleBy19(number)) {
            return false;
        }

        if (this.divisibleBy23(number)) {
            return false;
        }

        if (this.divisibleBy29(number)) {
            return false;
        }

        if (this.divisibleBy31(number)) {
            return false;
        }

        if (this.divisibleBy37(number)) {
            return false;
        }

        if (this.divisibleBy41(number)) {
            return false;
        }

        if (this.divisibleBy43(number)) {
            return false;
        }

        if (this.divisibleBy47(number)) {
            return false;
        }

        return true;
    }
}

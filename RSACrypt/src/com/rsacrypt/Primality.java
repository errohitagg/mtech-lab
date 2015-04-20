package com.rsacrypt;

public class Primality {

    private boolean divisibleBy3(MyBigInteger number) {

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

    private boolean divisibleBy5(MyBigInteger number) {

        int size = number.size();
        int numbers[] = number.numbers();

        if (numbers[size-1] % 5 == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean divisibleByN(MyBigInteger number, int n, int val, boolean add) {

        MyBigInteger final_number = new MyBigInteger(number);
        String str_final_number;
        int curr_number;

        while (final_number.size() > 1) {

            str_final_number = final_number.toString();
            curr_number = Character.digit(str_final_number.charAt(str_final_number.length() - 1), 10);
            curr_number *= val;
            final_number = new MyBigInteger(str_final_number.substring(0, str_final_number.length() - 1));
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

    private boolean divisibleBy7(MyBigInteger number) {

        return this.divisibleByN(number, 7, 2, false);
    }

    private boolean divisibleBy11(MyBigInteger number) {

        return this.divisibleByN(number, 11, 1, false);
    }

    private boolean divisibleBy13(MyBigInteger number) {

        return this.divisibleByN(number, 13, 4, true);
    }

    private boolean divisibleBy17(MyBigInteger number) {

        return this.divisibleByN(number, 17, 5, false);
    }

    private boolean divisibleBy19(MyBigInteger number) {

        return this.divisibleByN(number, 19, 2, true);
    }

    private boolean divisibleBy23(MyBigInteger number) {

        return this.divisibleByN(number, 23, 7, true);
    }

    private boolean divisibleBy29(MyBigInteger number) {

        return this.divisibleByN(number, 29, 3, true);
    }

    private boolean divisibleBy31(MyBigInteger number) {

        return this.divisibleByN(number, 31, 3, false);
    }

    private boolean divisibleBy37(MyBigInteger number) {

        return this.divisibleByN(number, 37, 11, false);
    }

    private boolean divisibleBy41(MyBigInteger number) {

        return this.divisibleByN(number, 41, 4, false);
    }

    private boolean divisibleBy43(MyBigInteger number) {

        return this.divisibleByN(number, 43, 13, true);
    }

    private boolean divisibleBy47(MyBigInteger number) {

        return this.divisibleByN(number, 47, 14, false);
    }

    private boolean checkMillerRabin(MyBigInteger number, MyBigInteger remainder, MyBigInteger base, int power) {

        MyBigInteger result = base.exponent_modulus(remainder, number);
        MyBigInteger compare_number = number.subtract(1);
        MyBigInteger one = new MyBigInteger("1");
        int i;

        if (result.compareTo(one) == 0) {
            return false;
        }

        for (i = 0; i < power; i++) {

            if (result.compareTo(compare_number) == 0) {
                return false;
            } else {
                result = result.multiply_modulus(result, number);
            }
        }

        return true;
    }

    private boolean millerRabin(MyBigInteger number) {

        MyBigInteger base[] = new MyBigInteger[]{new MyBigInteger("2"), new MyBigInteger("3"), new MyBigInteger("5"),
                new MyBigInteger("7"), new MyBigInteger("11"), new MyBigInteger("13"), new MyBigInteger("17"),
                new MyBigInteger("19"), new MyBigInteger("23"), new MyBigInteger("29"), new MyBigInteger("31"),
                new MyBigInteger("37"), new MyBigInteger("41"), new MyBigInteger("43"), new MyBigInteger("47")};

        int length = base.length, power = 0, i;
        MyBigInteger remainder = number.subtract(1);

        while (remainder.modulus(MyBigInteger.two).compareTo(MyBigInteger.zero) == 0) {

            power++;
            remainder = remainder.divide(MyBigInteger.two);
        }

        for (i = 0; i < length; i++) {

            if (this.checkMillerRabin(number, remainder, base[i], power)) {
                return true;
            }
        }

        return false;
    }

    public boolean isPrime(MyBigInteger number) {

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

        if (this.millerRabin(number)) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        Primality primeTest = new Primality();
        MyBigInteger numbers[] = new MyBigInteger[]{new MyBigInteger("561"), new MyBigInteger("27"), new MyBigInteger("61"),
                new MyBigInteger("4033")};
        int i;

        for (i = 0; i < numbers.length; i++) {

            System.out.print(numbers[i] + " - ");
            if (primeTest.isPrime(numbers[i])) {
                System.out.println("Prime");
            } else {
                System.out.println("Composite");
            }
        }

    }
}

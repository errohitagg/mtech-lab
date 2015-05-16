package com.rsacrypt;

import java.util.Arrays;

public class MyBigInteger {

    private static final int BASE = 10000;
    private static final int BASE_SIZE = 4;
    private static final String BASE_PADDING = "0000";

    private int[] numbers;
    private int size;
    private boolean isNegative;

    public static MyBigInteger ZERO = new MyBigInteger("0");
    public static MyBigInteger ONE = new MyBigInteger("1");
    public static MyBigInteger TWO = new MyBigInteger("2");
    public static MyBigInteger TEN = new MyBigInteger("10");

    public MyBigInteger() {

        this("0");
    }

    public MyBigInteger(String number) {

        this(number.toCharArray());
    }

    public MyBigInteger(char[] number) {

        this.validate(number);

        int length = number.length, i, j, positions;
        int size = (length % BASE_SIZE == 0) ? (length / BASE_SIZE) : ((length / BASE_SIZE) + 1);

        this.numbers = new int[size];

        for (i = length, j = size - 1; i > 0; i -= BASE_SIZE, j--) {
            positions = (i >= BASE_SIZE) ? BASE_SIZE : i;
            this.numbers[j] = Integer.parseInt(new String(number, i - positions, positions));
        }

        this.trim();
    }

    public MyBigInteger(int[] numbers) {

        this.numbers = numbers;
        this.trim();
    }

    public MyBigInteger(MyBigInteger integer) {

        this.numbers = integer.numbers;
        this.size = integer.size;
        this.isNegative = integer.isNegative;
    }

    private void validate(char[] number) {

        int length = number.length, i;

        for (i = 0; i < length; i++) {
            if (number[i] == '-') {
                number[i] = '0';
                this.isNegative = true;
            } else if (number[i] < '0' || number[i] > '9') {
                throw new IllegalArgumentException("Number must contain only digits");
            }
        }
    }

    private void trim() {

        while (this.numbers[0] == 0 && this.numbers.length > 1) {
            this.numbers = Arrays.copyOfRange(this.numbers, 1, this.numbers.length);
        }

        this.size = this.numbers.length;
    }

    private MyBigInteger[] divide_and_modulus(MyBigInteger that) {

        if (that.compareTo(MyBigInteger.ZERO) == 0) {
            throw new ArithmeticException("Can't divide by zero");
        }

        if (this.compareTo(that) == -1) {
            return new MyBigInteger[]{new MyBigInteger(), new MyBigInteger(this)};
        }

        String result = "", first_num = this.toString();
        int length = first_num.length(), current_index = that.toString().length();
        int i, current_quotient;
        MyBigInteger first_temp_num = new MyBigInteger(first_num.substring(0, current_index)), second_temp_num;

        do {
            second_temp_num = new MyBigInteger();
            for (i = 1, current_quotient = 0; i <= 10; i++, current_quotient++) {
                second_temp_num = that.multiply(new MyBigInteger(String.valueOf(i)));
                if (first_temp_num.compareTo(second_temp_num) == -1) {
                    break;
                }
            }

            result += current_quotient;
            second_temp_num = second_temp_num.subtract(that);
            first_temp_num = first_temp_num.subtract(second_temp_num);

            if (current_index < length) {
                do {
                    first_temp_num = first_temp_num.multiply(MyBigInteger.TEN);
                    first_temp_num = first_temp_num.add(new MyBigInteger(first_num.substring(current_index, ++current_index)));
                    if (first_temp_num.compareTo(that) == -1) {
                        result += "0";
                    }
                } while (current_index < length && first_temp_num.compareTo(that) == -1);
            }

        } while (first_temp_num.compareTo(that) != -1);

        return new MyBigInteger[]{new MyBigInteger(result), new MyBigInteger(first_temp_num)};
    }

    public int size() {

        return this.size;
    }

    public int[] numbers() {

        return this.numbers;
    }

    public MyBigInteger add(MyBigInteger that) {

        boolean isResultNegative = false;
        int resultSize = (this.size > that.size ? this.size : that.size) + 1;
        int[] result = new int[resultSize];
        int number, carry = 0, i, j, k;

        if (this.isNegative && !that.isNegative) {

            MyBigInteger newInteger = new MyBigInteger(this);
            newInteger.isNegative = false;
            return that.subtract(newInteger);

        } else if (!this.isNegative && that.isNegative) {

            MyBigInteger newInteger = new MyBigInteger(that);
            newInteger.isNegative = false;
            return this.subtract(newInteger);

        } else if (this.isNegative && that.isNegative) {
            isResultNegative = true;
        }

        for (i = this.size - 1, j = that.size - 1, k = resultSize - 1; i >= 0 && j >= 0; i--, j--, k--) {
            number = carry + this.numbers[i] + that.numbers[j];
            result[k] = number % BASE;
            carry = number / BASE;
        }

        for (; i >= 0; i--, k--) {
            number = carry + this.numbers[i];
            result[k] = number % BASE;
            carry = number / BASE;
        }

        for (; j >= 0; j--, k--) {
            number = carry + that.numbers[j];
            result[k] = number % BASE;
            carry = number / BASE;
        }

        if (carry > 0) {
            result[k] = carry;
        }

        MyBigInteger resultInteger = new MyBigInteger(result);
        if (resultInteger.compareTo(MyBigInteger.ZERO) != 0) {
            resultInteger.isNegative = isResultNegative;
        }

        return resultInteger;
    }

    public MyBigInteger subtract(MyBigInteger that) {

        int resultSize = (this.size > that.size ? this.size : that.size) + 1;
        int[] result = new int[resultSize];
        int number, i, j, k;
        boolean borrow = false, isResultNegative = false;
        MyBigInteger first, second;

        if (this.isNegative && !that.isNegative) {

            MyBigInteger newInteger = new MyBigInteger(this);
            newInteger.isNegative = false;

            MyBigInteger resultInteger = newInteger.add(that);
            resultInteger.isNegative = true;
            return resultInteger;

        } else if (!this.isNegative && that.isNegative) {

            MyBigInteger newInteger = new MyBigInteger(that);
            newInteger.isNegative = false;
            return this.add(newInteger);
        }

        if (this.compareTo(that) > 0) {
            isResultNegative = this.isNegative;
            first = this;
            second = that;
        } else {
            isResultNegative = !that.isNegative;
            first = that;
            second = this;
        }


        for (i = first.size - 1, j = second.size - 1, k = resultSize - 1; i >= 0 && j >= 0; i--, j--, k--) {
            number = first.numbers[i];
            if (borrow) {
                number = number - 1;
                borrow = false;
            }

            if (number >= second.numbers[j]) {
                result[k] = number - second.numbers[j];
            } else {
                if (i > 0) {
                    number = BASE + number;
                }
                result[k] = number - second.numbers[j];
                borrow = true;
            }
        }

        for (; i >= 0; i--, k--) {
            number = first.numbers[i];
            if (borrow) {
                number = number - 1;
                borrow = false;
            }
            result[k] = number;
        }

        MyBigInteger resultInteger = new MyBigInteger(result);
        if (resultInteger.compareTo(MyBigInteger.ZERO) != 0) {
            resultInteger.isNegative = isResultNegative;
        }

        return resultInteger;
    }

    public MyBigInteger multiply(MyBigInteger that) {

        int resultSize = this.size + that.size + 1;
        int[] temp_result;
        int number, i, j, k, carry;
        MyBigInteger result = new MyBigInteger();

        for (i = this.size - 1; i >= 0; i--) {
            temp_result = new int[resultSize];
            carry = 0;
            for (k = resultSize - 1; k > resultSize + i - this.size; k--) {
                temp_result[k] = 0;
            }

            for (j = that.size - 1; j >= 0; j--, k--) {
                number = (this.numbers[i] * that.numbers[j]) + carry;
                temp_result[k] = number % BASE;
                carry = number / BASE;
            }

            if (carry > 0) {
                temp_result[k] = carry;
            }
            result = result.add(new MyBigInteger(temp_result));
        }

        if ((this.isNegative || that.isNegative) && !(this.isNegative && that.isNegative) && result.compareTo(MyBigInteger.ZERO) != 0) {
            result.isNegative = true;
        }

        return result;
    }

    public MyBigInteger divide(MyBigInteger that) {

        MyBigInteger[] divide_modulus = this.divide_and_modulus(that);
        return divide_modulus[0];
    }

    public MyBigInteger modulus(MyBigInteger that) {

        MyBigInteger[] divide_modulus = this.divide_and_modulus(that);
        return divide_modulus[1];
    }

    public int compareTo(MyBigInteger that) {

        if (this.size > that.size) {
            return 1;
        } else if (this.size < that.size) {
            return -1;
        } else {
            for (int i = 0; i < this.size; i++) {
                if (this.numbers[i] > that.numbers[i]) {
                    return 1;
                } else if (this.numbers[i] < that.numbers[i]) {
                    return -1;
                }
            }
        }

        return 0;
    }

    public boolean isPrime() {

        Primality primeNumberTest = new Primality();
        return primeNumberTest.isPrime(this);
    }

    public boolean isCoPrime(MyBigInteger that) {

        MyBigInteger dividend = new MyBigInteger(this), divisor = new MyBigInteger(that);
        MyBigInteger result[];

        do {
            result = dividend.divide_and_modulus(divisor);
            dividend = divisor;
            divisor = result[1];
        } while (divisor.compareTo(MyBigInteger.ZERO) != 0);

        if (dividend.compareTo(MyBigInteger.ONE) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public MyBigInteger multiply_modulus(MyBigInteger that, MyBigInteger modulus) {

        MyBigInteger result = this.multiply(that);
        result = result.modulus(modulus);
        return result;
    }

    public MyBigInteger exponent_modulus(MyBigInteger that, MyBigInteger modulus) {

        MyBigInteger result = new MyBigInteger("1");
        MyBigInteger operating_number = new MyBigInteger(this);
        String binary_exponent = that.toBinary();
        int length = binary_exponent.length(), i;

        for (i = length - 1; i >= 0; i--) {
            if (binary_exponent.charAt(i) == '1') {
                result = result.multiply_modulus(operating_number, modulus);
            }
            operating_number = operating_number.multiply_modulus(operating_number, modulus);
        }

        return result;
    }

    public MyBigInteger inverse_modulus(MyBigInteger that) {

        MyBigInteger first = new MyBigInteger(that), second = new MyBigInteger(this);
        MyBigInteger t1 = new MyBigInteger(MyBigInteger.ZERO), t2 = new MyBigInteger(MyBigInteger.ONE);
        MyBigInteger divide_and_modulus[], temp_result;

        do {
            divide_and_modulus = first.divide_and_modulus(second);
            first = second;
            second = divide_and_modulus[1];
            temp_result = t1.subtract(t2.multiply(divide_and_modulus[0]));
            t1 = t2;
            t2 = temp_result;
        } while(second.compareTo(MyBigInteger.ZERO) != 0);

        if (t1.isNegative) {
            t1 = t1.add(that);
        }

        return t1;
    }

    public String toBinary() {

        String result = "";
        MyBigInteger number = new MyBigInteger(this);
        MyBigInteger operation_result[];

        while (number.compareTo(MyBigInteger.ZERO) != 0) {
            operation_result = number.divide_and_modulus(MyBigInteger.TWO);
            number = operation_result[0];
            result = operation_result[1].toString() + result;
        }

        return result;
    }

	public String toString() {

        String number = "", temp_number = "";
        if (this.isNegative) {
            number += "-";
        }

        for (int i = 0; i < this.size; i++) {
            temp_number = String.valueOf(this.numbers[i]);
            if (temp_number.length() < BASE_SIZE && i > 0) {
                temp_number = BASE_PADDING.substring(0, BASE_SIZE - temp_number.length()) + temp_number;
            }
            number += temp_number;
        }

        return number;
    }

    public static void main(String[] args) {

        try {

            MyBigInteger myObj1 = new MyBigInteger("34093452948438923492389902482342012333");
            MyBigInteger myObj2 = new MyBigInteger("14482342549401412414905809238523249");
            MyBigInteger myObj3 = new MyBigInteger("95647806479275528135733781266203904794419563064407");

            MyBigInteger ansAdd = myObj1.add(myObj2);
            MyBigInteger ansSub1 = myObj1.subtract(myObj2);
            MyBigInteger ansSub2 = myObj2.subtract(myObj1);
            MyBigInteger ansMul = myObj1.multiply(myObj2);
            MyBigInteger ansDiv = myObj1.divide(myObj2);
            MyBigInteger ansMod = myObj1.modulus(myObj2);
            MyBigInteger ansInv = myObj2.inverse_modulus(myObj1);
            String ansBinary = myObj1.toBinary();
            int ansCmp = myObj1.compareTo(myObj2);
            boolean ansPrime1 = myObj1.isPrime();
            boolean ansPrime2 = myObj3.isPrime();
            boolean ansCoPrime = myObj1.isCoPrime(myObj2);

            System.out.println("Number1 = " + myObj1);
            System.out.println("Number2 = " + myObj2);
            System.out.println("Number3 = " + myObj3);
            System.out.println();
            System.out.println("Number1 + Number2 = " + ansAdd);
            System.out.println("Number1 - Number2 = " + ansSub1);
            System.out.println("Number1 - Number2 = " + ansSub2);
            System.out.println("Number1 * Number2 = " + ansMul);
            System.out.println("Number1 / Number2 = " + ansDiv);
            System.out.println("Number1 % Number2 = " + ansMod);
            System.out.println("Number2 Inverse (mod Number1) = " + ansInv);
            System.out.println("Number1 compare Number2 = " + ansCmp);
            System.out.println("To Binary (Number1) = " + ansBinary);
            System.out.println("Number1 Prime    = " + ansPrime1);
            System.out.println("Number3 Prime    = " + ansPrime2);
            System.out.println("Number1 & Number2 CoPrime = " + ansCoPrime);
            System.out.println("Done");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
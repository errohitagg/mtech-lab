package com.rsacrypt;

import java.util.Arrays;

public class BigInteger {

    private static final int BASE = 10000;
    private static final int BASE_SIZE = 4;
    private static final String BASE_PADDING = "0000";

    private int[] numbers;
    private int size;

    public BigInteger() {

        this("0");
    }

    public BigInteger(String number) {

        this(number.toCharArray());
    }

    public BigInteger(char[] number) {

        this.validate(number);

        int length = number.length;
        int size = (length % BASE_SIZE == 0) ? (length / BASE_SIZE) : ((length / BASE_SIZE) + 1);
        int i, j, positions;

        this.numbers = new int[size];
        for (i = length, j = size - 1; i > 0; i -= BASE_SIZE, j--) {
            positions = (i >= BASE_SIZE) ? BASE_SIZE : i;
            this.numbers[j] = Integer.parseInt(new String(number, i - positions, positions));
        }

        this.trim();
    }

    public BigInteger(int[] numbers) {

        this.numbers = numbers;
        this.trim();
    }

    public BigInteger(BigInteger integer) {

        this.numbers = integer.numbers;
        this.size = integer.size;
    }

    private void validate(char[] number) {

        int length = number.length, i;
        for (i = 0; i < length; i++) {
            if (number[i] < '0' || number[i] > '9') {
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

    private static BigInteger[] divide_and_modulus(BigInteger first, BigInteger second) {

        if (second.compare(new BigInteger()) == 0) {
            throw new ArithmeticException("Can't divide by zero");
        }

        if (first.compare(second) == -1) {
            return new BigInteger[]{new BigInteger(), new BigInteger(first)};
        }

        String result = "", first_num = first.toString();
        int length = first_num.length(), current_index = second.toString().length();
        int i, current_quotient;
        BigInteger first_temp_num = new BigInteger(first_num.substring(0, current_index)), second_temp_num;

        do {

            second_temp_num = new BigInteger();
            for (i = 1, current_quotient = 0; i <= 10; i++, current_quotient++) {

                second_temp_num = BigInteger.multiply(second, i);
                if (first_temp_num.compare(second_temp_num) == -1) {
                    break;
                }
            }

            result += current_quotient;
            second_temp_num.subtract(second);
            first_temp_num.subtract(second_temp_num);
            if (current_index < length) {
                do {
                    first_temp_num.multiply(10);
                    first_temp_num.add(Integer.parseInt(first_num.substring(current_index, ++current_index)));
                    if (first_temp_num.compare(second) == -1) {
                        result += "0";
                    }
                } while (current_index < length && first_temp_num.compare(second) == -1);
            }

        } while (first_temp_num.compare(second) != -1);

        return new BigInteger[]{new BigInteger(result), new BigInteger(first_temp_num)};
    }

    private static BigInteger[] divide_and_modulus(BigInteger first, int second) {

        if (second == 0) {
            throw new ArithmeticException("Can't divide by zero");
        }

        if (first.compare(new BigInteger(String.valueOf(second))) == -1) {
            return new BigInteger[]{new BigInteger(), new BigInteger(first)};
        }

        String result = "";
        int length = first.size, current_number;
        int i, quotient, remainder = 0;

        for (i = 0; i < length; i++) {

            if (remainder > 0) {
                current_number = (remainder * BASE) + first.numbers[i];
            } else {
                current_number = first.numbers[i];
            }

            quotient = current_number / second;
            remainder = current_number % second;
            result += String.format("%04d", quotient);
        }

        return new BigInteger[]{new BigInteger(result), new BigInteger(String.valueOf(remainder))};
    }

    public int size() {

        return this.size;
    }

    public int[] numbers() {

        return this.numbers;
    }

    public static BigInteger add(BigInteger first, int second) {

        int resultSize = first.size + 1;
        int[] result = new int[resultSize];
        int number, carry = 0, i = first.size - 1, k = resultSize - 1;

        number = carry + first.numbers[i--] + second;
        result[k--] = number % BASE;
        carry = number / BASE;

        for (; i >= 0; i--, k--) {
            number = carry + first.numbers[i];
            result[k] = number % BASE;
            carry = number / BASE;
        }

        if (carry > 0) {
            result[k] = carry;
        }

        return new BigInteger(result);
    }

    public static BigInteger add(BigInteger first, BigInteger second) {

        int resultSize = (first.size > second.size ? first.size : second.size) + 1;
        int[] result = new int[resultSize];
        int number, carry = 0, i, j, k;

        for (i = first.size - 1, j = second.size - 1, k = resultSize - 1; i >= 0 && j >= 0; i--, j--, k--) {
            number = carry + first.numbers[i] + second.numbers[j];
            result[k] = number % BASE;
            carry = number / BASE;
        }

        for (; i >= 0; i--, k--) {
            number = carry + first.numbers[i];
            result[k] = number % BASE;
            carry = number / BASE;
        }

        for (; j >= 0; j--, k--) {
            number = carry + second.numbers[j];
            result[k] = number % BASE;
            carry = number / BASE;
        }

        if (carry > 0) {
            result[k] = carry;
        }

        return new BigInteger(result);
    }

    public static BigInteger subtract(BigInteger first, int second) {

        int resultSize = first.size + 1;
        int[] result = new int[resultSize];
        int number, i = first.size - 1, j, k = resultSize - 1;
        boolean borrow = false;

        number = first.numbers[i--];
        if (number >= second) {
            result[k--] = number - second;
        } else {
            number = BASE + number;
            result[k--] = number - second;
            borrow = true;
        }

        for (; i >= 0; i--, k--) {

            number = first.numbers[i];
            if (borrow) {
                number = number - 1;
                borrow = false;
            }
            result[k] = number;
        }

        return new BigInteger(result);
    }

    public static BigInteger subtract(BigInteger first, BigInteger second) {

        int resultSize = (first.size > second.size ? first.size : second.size) + 1;
        int[] result = new int[resultSize];
        int number, i, j, k;
        boolean borrow = false;

        for (i = first.size - 1, j = second.size - 1, k = resultSize - 1; i >= 0 && j >= 0; i--, j--, k--) {

            number = first.numbers[i];
            if (borrow) {
                number = number - 1;
                borrow = false;
            }

            if (number >= second.numbers[j]) {
                result[k] = number - second.numbers[j];
            } else {
                number = BASE + number;
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

        return new BigInteger(result);
    }

    public static BigInteger multiply(BigInteger first, int second) {

        int resultSize = first.size + 1;
        int[] temp_result = new int[resultSize];
        int number, i, j, k, carry = 0;

        for (i = first.size - 1, k = resultSize - 1; i >= 0; i--, k--) {

            number = (first.numbers[i] * second) + carry;
            temp_result[k] = number % BASE;
            carry = number / BASE;
        }

        if (carry > 0) {
            temp_result[k] = carry;
        }

        return new BigInteger(temp_result);
    }

    public static BigInteger multiply(BigInteger first, BigInteger second) {

        int resultSize = first.size + second.size + 1;
        int[] temp_result;
        int number, i, j, k, carry;
        BigInteger result = new BigInteger();

        for (i = first.size - 1; i >= 0; i--) {

            temp_result = new int[resultSize];
            carry = 0;
            for (k = resultSize - 1; k > resultSize + i - first.size; k--) {
                temp_result[k] = 0;
            }

            for (j = second.size - 1; j >= 0; j--, k--) {

                number = (first.numbers[i] * second.numbers[j]) + carry;
                temp_result[k] = number % BASE;
                carry = number / BASE;
            }

            if (carry > 0) {
                temp_result[k] = carry;
            }

            result.add(new BigInteger(temp_result));
        }

        return result;
    }

    public static BigInteger divide(BigInteger first, BigInteger second) {

        BigInteger[] divide_modulus = divide_and_modulus(first, second);
        return divide_modulus[0];
    }

    public static BigInteger modulus(BigInteger first, BigInteger second) {

        BigInteger[] divide_modulus = divide_and_modulus(first, second);
        return divide_modulus[1];
    }

    public static int compare(BigInteger first, BigInteger second) {

        if (first.size > second.size) {
            return 1;
        } else if (first.size < second.size) {
            return -1;
        } else {

            for (int i = 0; i < first.size; i++) {
                if (first.numbers[i] > second.numbers[i]) {
                    return 1;
                } else if (first.numbers[i] < second.numbers[i]) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public static boolean isPrime(BigInteger number) {

        Primality primeNumberTest = new Primality();
        return primeNumberTest.isPrime(number);
    }

    public static String toBinary(BigInteger number) {

        String result = "";
        BigInteger zero = new BigInteger();
        BigInteger operation_result[];

        while (number.compare(zero) != 0) {

            operation_result = BigInteger.divide_and_modulus(number, 2);
            number = operation_result[0];
            result = operation_result[1] + result;
        }

        return result;
    }

    public static BigInteger multiply_modulus(BigInteger first, BigInteger second, BigInteger modulus) {

        BigInteger result = BigInteger.multiply(first, second);
        result.modulus(modulus);
        return result;
    }

    public static BigInteger exponent_modulus(BigInteger number, BigInteger exponent, BigInteger modulus) {

        BigInteger result = new BigInteger("1");
        BigInteger operating_number = new BigInteger(number);
        String binary_exponent = exponent.toBinary();
        int length = binary_exponent.length(), i;

        for (i = length - 1; i >= 0; i--) {

            if (binary_exponent.charAt(i) == '1') {
                result.multiply_modulus(operating_number, modulus);
            }

            operating_number.multiply_modulus(operating_number, modulus);
        }

        return result;
    }

	public void add(int that) {

        BigInteger result = add(this, that);
        this.numbers = result.numbers;
        this.size = result.size;
    }

    public void add(BigInteger that) {

        BigInteger result = add(this, that);
        this.numbers = result.numbers;
        this.size = result.size;
    }

    public void subtract(int that) {

        BigInteger result = subtract(this, that);
        this.numbers = result.numbers;
        this.size = result.size;
    }

    public void subtract(BigInteger that) {

        BigInteger result = subtract(this, that);
        this.numbers = result.numbers;
        this.size = result.size;
    }

    public void multiply(int that) {

        BigInteger result = multiply(this, that);
        this.numbers = result.numbers;
        this.size = result.size;
    }

    public void multiply(BigInteger that) {

        BigInteger result = multiply(this, that);
        this.numbers = result.numbers;
        this.size = result.size;
    }

    public void divide(BigInteger that) {

        BigInteger result = divide(this, that);
        this.numbers = result.numbers;
        this.size = result.size;
    }

    public void modulus(BigInteger that) {

        BigInteger result = modulus(this, that);
        this.numbers = result.numbers;
        this.size = result.size;
    }

    public int compare(BigInteger that) {

        return compare(this, that);
    }

    public boolean isPrime() {

        return isPrime(this);
    }

    public String toBinary() {

        return toBinary(this);
    }

    public void multiply_modulus(BigInteger that, BigInteger modulus) {

        BigInteger result = multiply_modulus(this, that, modulus);
        this.numbers = result.numbers;
        this.size = result.size;
    }

    public String toString() {

        String number = "", temp_number = "";
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
            BigInteger obj1 = new BigInteger("34093452948438923492389902482342012333"); // 34093452948438923492389902482342012333
            BigInteger obj2 = new BigInteger("14482342549401412414905809238523249"); // 14482342549401412414905809238523249
            BigInteger obj3 = new BigInteger("95647806479275528135733781266203904794419563064407"); // 95647806479275528135733781266203904794419563064407
			int num1 = 997;
            BigInteger ansAdd = BigInteger.add(obj1, obj2);
            BigInteger expAdd = new BigInteger("34107935290988324904804808291580535582");
            BigInteger ansSub = BigInteger.subtract(obj1, obj2);
            BigInteger expSub = new BigInteger("34078970605889522079974996673103489084");
            BigInteger ansMul = BigInteger.multiply(obj1, obj2);
            BigInteger expMul = new BigInteger("493753064291192060102104412882477667505149871006101352592724307865229917");
            BigInteger ansMulInt = BigInteger.multiply(obj1, num1);
            BigInteger expMulInt = new BigInteger("33991172589593606721912732774894986296001");
            BigInteger ansDiv = BigInteger.divide(obj1, obj2);
            BigInteger expDiv = new BigInteger("2354");
            BigInteger ansDiv2 = BigInteger.divide(obj2, obj1);
            BigInteger expDiv2 = new BigInteger("0");
            BigInteger ansMod = BigInteger.modulus(obj1, obj2);
            BigInteger expMod = new BigInteger("2018587147998667701627534858284187");
            BigInteger ansMod2 = BigInteger.modulus(obj2, obj1);
            BigInteger expMod2 = new BigInteger("14482342549401412414905809238523249");
            String ansBinary = BigInteger.toBinary(obj1);
            String expBinary = "11001101001100010100100000010011110101111000110001001010000111101100000011000101001000110010011100010000110011110010110101101";
            int ansCmp1 = BigInteger.compare(obj1, obj2);
            int ansCmp2 = BigInteger.compare(obj2, obj1);
            boolean ansPrime1 = BigInteger.isPrime(obj1);
            boolean ansPrime2 = BigInteger.isPrime(obj2);
            boolean ansPrime3 = BigInteger.isPrime(obj3);
            System.out.println("Number1 = " + obj1);
            System.out.println("Number2 = " + obj2);
            System.out.println("Number3 = " + obj3);
            System.out.println();
            System.out.println("Number1 + Number2 = " + ansAdd);
            System.out.println("Expected Answer   = " + expAdd);
            System.out.println("Result = " + (BigInteger.compare(ansAdd, expAdd) == 0 ? "Pass" : "Fail"));
            System.out.println("Number1 - Number2 = " + ansSub);
            System.out.println("Expected Answer   = " + expSub);
            System.out.println("Result = " + (BigInteger.compare(ansSub, expSub) == 0 ? "Pass" : "Fail"));
            System.out.println("Number1 * Number2 = " + ansMul);
            System.out.println("Expected Answer   = " + expMul);
            System.out.println("Result = " + (BigInteger.compare(ansMul, expMul) == 0 ? "Pass" : "Fail"));
            System.out.println("Number1 * Number2 = " + ansMulInt);
            System.out.println("Expected Answer   = " + expMulInt);
            System.out.println("Result = " + (BigInteger.compare(ansMulInt, expMulInt) == 0 ? "Pass" : "Fail"));
            System.out.println("Number1 / Number2 = " + ansDiv);
            System.out.println("Expected Answer   = " + expDiv);
            System.out.println("Result = " + (BigInteger.compare(ansDiv, expDiv) == 0 ? "Pass" : "Fail"));
            System.out.println("Number1 % Number2 = " + ansMod);
            System.out.println("Expected Answer   = " + expMod);
            System.out.println("Result = " + (BigInteger.compare(ansMod, expMod) == 0 ? "Pass" : "Fail"));
            System.out.println("Number2 / Number1 = " + ansDiv2);
            System.out.println("Expected Answer   = " + expDiv2);
            System.out.println("Result = " + (BigInteger.compare(ansDiv2, expDiv2) == 0 ? "Pass" : "Fail"));
            System.out.println("Number2 % Number1 = " + ansMod2);
            System.out.println("Expected Answer   = " + expMod2);
            System.out.println("Result = " + (BigInteger.compare(ansMod2, expMod2) == 0 ? "Pass" : "Fail"));
            System.out.println("toBinary (Number1) = " + ansBinary);
            System.out.println("Expected Answer   = " + expBinary);
            System.out.println("Result = " + (ansBinary.compareTo(expBinary) == 0 ? "Pass" : "Fail"));
            System.out.println("Number1 > Number2 = " + ansCmp1);
            System.out.println("Number1 < Number2 = " + ansCmp2);
            System.out.println("Number1 Prime = " + ansPrime1);
            System.out.println("Number2 Prime = " + ansPrime2);
            System.out.println("Number3 Prime = " + ansPrime3);
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

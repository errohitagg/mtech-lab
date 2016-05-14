/**
 * @title       Determine whether a given a number is palindrome or not.
 *
 * @author      Rohit Aggarwal [2K14/CSE/502]
 * @created
 */

import java.util.Scanner;

public class Program05 {

    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);
        Integer number, reverse_number, temp_number, digit;

        System.out.print("Enter a number: ");
        number = input.nextInt();
        reverse_number = 0;
        temp_number = number;

        while (temp_number != 0) {
            digit = temp_number % 10;
            reverse_number = (reverse_number * 10) + digit;
            temp_number = temp_number / 10;
        }

        if (reverse_number.equals(number)) {
            System.out.printf("\nNumber %d is a palindrome\n", number);
        } else {
            System.out.printf("\nNumber %d is not a palindrome\n", number);
        }
    }
}

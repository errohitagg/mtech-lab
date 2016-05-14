/**
 * @title       Write a program for the determination of the nature of roots of a quadratic
 *              equation. The range of the values may be inputted from the user. The program
 *              output may have one of the following words: not a quadratic equation, real
 *              roots, equal roots, imaginary roots. Design the test cases using the
 *              following techniques:
 *                  a. Boundary Value Analysis
 *                  b. Robustness Analysis
 *                  c. Worst Case Analysis
 *
 * @author      Rohit Aggarwal [2K14/CSE/502]
 * @created
 */

import java.util.Scanner;

public class Program01 {

    public static void quadratic(Integer id, Integer a, Integer b, Integer c) {

        Integer discriminant;
        discriminant = (b * b) - (4 * a * c);
        String output;

        if (a == 0) {
            output = "Not a quadratic equation";
        } else if (discriminant == 0) {
            output = "Equal Roots";
        } else if (discriminant > 0) {
            output = "Real Roots";
        } else {
            output = "Imaginary Roots";
        }

        System.out.printf("%-8d%-8d%-8d%-8d%s\n", id, a, b, c, output);
    }

    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);
        Integer choice, minimum, maximum, mid_point, case_count;
        Integer[] cases;

        do {

            System.out.println("Select one of the following analysis techniques: ");
            System.out.println("1. Boundary Value Analysis");
            System.out.println("2. Robustness Analysis");
            System.out.println("3. Worst Case Analysis");
            System.out.println("4. Quit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            if (choice == 4) {
                break;
            }

            switch (choice) {

                case 1:
                    System.out.println("\n=======================");
                    System.out.println("Boundary Value Analysis");
                    System.out.println("=======================\n");
                    System.out.print("Enter Minimum: ");
                    minimum = input.nextInt();
                    System.out.print("Enter Maximum: ");
                    maximum = input.nextInt();

                    if (maximum < minimum) {
                        System.out.println("\nMaximum must be greater than minimum");
                        break;
                    }

                    mid_point = (minimum + maximum) / 2;
                    System.out.printf("%-8s%-8s%-8s%-8s%s\n", "Case", "a", "b", "c", "Output");

                    cases = new Integer[]{minimum, minimum + 1, maximum - 1, maximum};
                    case_count = 1;
                    for (Integer _case : cases) {
                        quadratic(case_count++, _case, mid_point, mid_point);
                        quadratic(case_count++, mid_point, _case, mid_point);
                        quadratic(case_count++, mid_point, mid_point, _case);
                    }
                    quadratic(case_count, mid_point, mid_point, mid_point);
                    break;

                case 2:
                    System.out.println("===================");
                    System.out.println("Robustness Analysis");
                    System.out.println("===================");
                    System.out.print("Enter Minimum: ");
                    minimum = input.nextInt();
                    System.out.print("Enter Maximum: ");
                    maximum = input.nextInt();

                    if (maximum < minimum) {
                        System.out.println("\nMaximum must be greater than minimum");
                        break;
                    }

                    mid_point = (minimum + maximum) / 2;
                    System.out.printf("%-8s%-8s%-8s%-8s%s\n", "Case", "a", "b", "c", "Output");

                    cases = new Integer[]{minimum - 1, minimum, minimum + 1, maximum - 1, maximum, maximum + 1};
                    case_count = 1;
                    for (Integer _case : cases) {
                        quadratic(case_count++, _case, mid_point, mid_point);
                        quadratic(case_count++, mid_point, _case, mid_point);
                        quadratic(case_count++, mid_point, mid_point, _case);
                    }
                    quadratic(case_count, mid_point, mid_point, mid_point);
                    break;

                case 3:
                    System.out.println("===================");
                    System.out.println("Worst Case Analysis");
                    System.out.println("===================");
                    System.out.print("Enter Minimum: ");
                    minimum = input.nextInt();
                    System.out.print("Enter Maximum: ");
                    maximum = input.nextInt();

                    if (maximum < minimum) {
                        System.out.println("\nMaximum must be greater than minimum");
                        break;
                    }

                    mid_point = (minimum + maximum) / 2;
                    System.out.printf("%-8s%-8s%-8s%-8s%s\n", "Case", "a", "b", "c", "Output");
                    cases = new Integer[]{minimum, minimum + 1, mid_point, maximum -1, maximum};
                    case_count = 1;
                    for (Integer case1 : cases) {
                        for (Integer case2 : cases) {
                            for (Integer case3 : cases) {
                                quadratic(case_count++, case1, case2, case3);
                            }
                        }
                    }

                    break;

                default:
                    System.out.println("\nInvalid input provided. Please try again later");
            }

            System.out.println("\n");

        } while (true);
    }
}
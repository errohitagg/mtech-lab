/**
 * @title       Write a program for the determination of the type of triangle. The program
 *              output may have one of the following words: not a triangle, scalene,
 *              isosceles, equilateral triangle or impossible case. Design the test
 *              cases using the Decision Table Testing technique.
 *
 * @author      Rohit Aggarwal [2K14/CSE/502]
 * @created
 */

import java.util.Random;

public class Program04 {

    public static int triangle(Integer id, Integer x, Integer y, Integer z) {

        switch (id) {
            case 0:
                if (x > (y + z)) {
                    return 0;
                } else {
                    return -1;
                }
            case 1:
                if (y > (x + z)) {
                    return 0;
                } else {
                    return -1;
                }
            case 2:
                if (z > (x + y)) {
                    return 0;
                } else {
                    return -1;
                }
            case 3:
                if (x == y && y == z) {
                    return 3;
                } else {
                    return -1;
                }
            case 6:
                if (x == y) {
                    return 2;
                } else {
                    return -1;
                }
            case 8:
                if (y == z) {
                    return 2;
                } else {
                    return -1;
                }
            case 9:
                if (x == z) {
                    return 2;
                } else {
                    return -1;
                }
            case 10:
                return 1;
            default:
                return -1;
        }
    }

    public static void main(String args[]) {

        System.out.println("======================");
        System.out.println("Decision Table Testing");
        System.out.println("======================");

        String[] condition_stub = {"C1: x < y + z", "C2: y < x + z", "C3: z < x + y", "C4: x = y", "C5: y = z", "C6: z = x"};
        String[] action_stub = {"Not triangle", "Scalene", "Isosceles", "Equilateral", "Impossible"};

        Integer[][] condition_entry = {
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {-1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {-1, -1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {-1, -1, -1, 1, 1, 1, 1, 0, 0, 0, 0},
            {-1, -1, -1, 1, 1, 0, 0, 1, 1, 0, 0},
            {-1, -1, -1, 1, 0, 1, 0, 1, 0, 1, 0}
        };
        Integer[][] action_entry = new Integer[5][];

        Integer x, y, z, output;
        Random select = new Random();

        System.out.printf("\n%-16s%32s\n", "Condition Stub", "Condition Entry");
        for (int i = 0; i < condition_entry.length; i++) {

            System.out.printf("%-16s", condition_stub[i]);
            for (int j = 0; j < condition_entry[0].length; j++) {
                if (condition_entry[i][j] == -1) {
                    System.out.printf("%4s", "-");
                } else {
                    System.out.printf("%4d", condition_entry[i][j]);
                }
            }
            System.out.println();
        }

        for (int i = 0; i < action_entry.length; i++) {
            action_entry[i] = new Integer[condition_entry[0].length];
            for (int j = 0; j < condition_entry[0].length; j++) {
                action_entry[i][j] = 0;
            }
        }

        for (int i = 0; i < condition_entry[0].length; i++) {

            if (condition_entry[0][i] == 0 || condition_entry[1][i] == 0 || condition_entry[2][i] == 0) {
                action_entry[0][i] = 1;
            } else if (condition_entry[3][i] == 0 && condition_entry[4][i] == 0 &&  condition_entry[5][i] == 0) {
                action_entry[1][i] = 1;
            } else if(condition_entry[3][i] == 1 && condition_entry[4][i] == 0 && condition_entry[5][i] == 0) {
                action_entry[2][i] = 1;
            } else if(condition_entry[4][i] == 1 && condition_entry[3][i] == 0 && condition_entry[5][i] == 0) {
                action_entry[2][i] = 1;
            } else if(condition_entry[5][i] == 1 && condition_entry[3][i] == 0 && condition_entry[4][i] == 0) {
                action_entry[2][i] = 1;
            } else if(condition_entry[3][i] == 1 && condition_entry[4][i] == 1 && condition_entry[5][i] == 1) {
                action_entry[3][i] = 1;
            } else if(condition_entry[3][i] == 1 && condition_entry[4][i] == 1 && condition_entry[5][i] == 0) {
                action_entry[4][i] = 1;
            } else if(condition_entry[3][i] == 1 && condition_entry[4][i] == 0 && condition_entry[5][i] == 1) {
                action_entry[4][i] = 1;
            } else if(condition_entry[3][i] == 0 && condition_entry[4][i] == 1 && condition_entry[5][i] == 1) {
                action_entry[4][i] = 1;
            }
        }

        System.out.printf("\n%-16s%32s\n", "Action Stub", "Action Entry");
        for (int i = 0; i < action_entry.length; i++) {

            System.out.printf("%-16s", action_stub[i]);
            for (int j = 0; j < action_entry[0].length; j++) {
                System.out.printf("%4d", action_entry[i][j]);
            }
            System.out.println();
        }

        System.out.println("\nTest Cases:");
        System.out.printf("%-8s%-8s%-8s%-8s%s\n", "Case", "x", "y", "z", "Output");

        for (int i = 0; i < condition_entry[0].length; i++) {

            if (i == 4 || i == 5 || i == 7) {

                System.out.printf("%-8d%-8s%-8s%-8s%s\n", i, '-', '-', '-', action_stub[4]);
                continue;
            }

            x = select.nextInt(100);
            y = select.nextInt(100);
            z = select.nextInt(100);
            output = triangle(i, x, y, z);

            if (output != -1) {
                System.out.printf("%-8d%-8d%-8d%-8d%s\n", i, x, y, z, action_stub[output]);
            } else {
                i--;
            }
        }
    }
}

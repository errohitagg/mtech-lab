/**
 * @title       Write a program to find the cyclomatic complexity of the palindrome program.
 *
 * @author      Rohit Aggarwal [2K14/CSE/502]
 * @created
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Program05Cycle {

    private static boolean do_while = false;
    private static boolean string = false;

    public static int cyclomatic(String line) {

        String text[] = line.split(" ");
        String temp_text[];
        int count = 0;

        for (String i : text) {

            switch (i) {
                case "if":
                    if (string == false) {
                        count += 1;
                    }
                    break;
                case "for":
                    if (string == false) {
                        count += 2;
                    }
                    break;
                case "while":
                    if (do_while) {
                        do_while = false;
                    } else if (string == false) {
                        count += 2;
                    }
                    break;
                case "do":
                    if (string == false) {
                        do_while = true;
                        count += 1;
                    }
                case "switch":
                    if (string == false) {
                        count += 1;
                    }
                    break;
                default:
                    temp_text = i.split("\"");
                    if (temp_text.length % 2 == 0) {
                        string = !string;
                    }
            }
        }

        return count;
    }

    public static void main(String[] args) {

        Scanner file;
        String filename = "src/Program05.java", line;
        Integer count = 0;

        try {

            file = new Scanner(new File(filename));
            while (file.hasNext()) {

                line = file.nextLine();
                count += cyclomatic(line);
            }

        } catch (FileNotFoundException e) {

            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println("Cyclomatic Complexity = " + (count + 1));
    }
}

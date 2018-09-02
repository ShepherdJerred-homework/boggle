import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class boggle {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("boggle.in");
        File outputFile = new File("boggle.out");

        Scanner scanner = new Scanner(inputFile);
        PrintWriter printWriter = new PrintWriter(outputFile);

        char[][] cube = new char[4][4];

        for (int i = 0; i < 4; i++) {
            char[] cubeLine = scanner.nextLine().toCharArray();
            cube[i] = cubeLine;
        }

        LinkedList<String> inputs = new LinkedList<>();

         while (scanner.hasNextLine()) {
             String line = scanner.nextLine();
             if (line.equals("*")) {
                 break;
             }
             inputs.add(line);
         }

        System.out.println(Arrays.deepToString(cube));

        for (String input : inputs) {
            System.out.println(input);

            boolean result = false;
            char[] inputChars = input.toCharArray();
            for (int row = 0; row < cube.length; row++) {
                for (int column = 0; column < cube.length; column++) {
                    if (findString(inputChars, 0, row, column, cube, new boolean[4][4])) {
                        result = true;
                        break;
                    }
                }
                if (result) {
                    break;
                }
            }
            if (result) {
                printWriter.println(String.format("%s  %s", input, "VALID"));
                System.out.println(String.format("%s  %s", input, "VALID"));
            } else {
                printWriter.println(String.format("%s  %s", input, "INVALID"));
                System.out.println(String.format("%s  %s", input, "INVALID"));
            }
        }
        printWriter.close();
    }

    public static boolean findString(char[] string, int currStringPos, int currRow, int currColumn, char[][] cube, boolean[][] usedChars) {
        if (currRow > 3 || currRow < 0 || currColumn > 3 || currColumn < 0) {
            return false;
        }

        if (usedChars[currRow][currColumn]) {
            System.out.println(String.format("Skipping r %s  c %s", currRow, currColumn));
            return false;
        }

        char currentChar = string[currStringPos];

        System.out.println(String.format("s %s  csp %s   r %s  c %s   cc %s", Arrays.toString(string), currStringPos, currRow, currColumn, currentChar));

        if (currentChar != cube[currRow][currColumn]) {
            return false;
        } else {
            System.out.println(String.format("%s found at r %s c %s", currentChar, currRow, currColumn));
            if (currStringPos == string.length - 1) {
                return true;
            }
        }

        usedChars[currRow][currColumn] = true;

        // l
        usedChars = copy(usedChars);
        if (findString(string, currStringPos + 1, currRow - 1, currColumn, cube, usedChars)) {
            return true;
        }

        // r
        usedChars = copy(usedChars);
        if (findString(string, currStringPos + 1, currRow + 1, currColumn, cube, usedChars)) {
            return true;
        }

        // u
        usedChars = copy(usedChars);
        if (findString(string, currStringPos + 1, currRow, currColumn + 1, cube, usedChars)) {
            return true;
        }

        // d
        usedChars = copy(usedChars);
        if (findString(string, currStringPos + 1, currRow, currColumn - 1, cube, usedChars)) {
            return true;
        }

        // l u
        usedChars = copy(usedChars);
        if (findString(string, currStringPos + 1, currRow - 1, currColumn + 1, cube, usedChars)) {
            return true;
        }

        // l d
        usedChars = copy(usedChars);
        if (findString(string, currStringPos + 1, currRow - 1, currColumn - 1, cube, usedChars)) {
            return true;
        }

        // r u
        usedChars = copy(usedChars);
        if (findString(string, currStringPos + 1, currRow + 1, currColumn + 1, cube, usedChars)) {
            return true;
        }

        // r d
        usedChars = copy(usedChars);
        if (findString(string, currStringPos + 1, currRow + 1, currColumn - 1, cube, usedChars)) {
            return true;
        }

        return false;
    }

    // https://stackoverflow.com/questions/1564832/how-do-i-do-a-deep-copy-of-a-2d-array-in-java
    public static boolean[][] copy(boolean[][] original) {
        boolean[][] copy = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }
}

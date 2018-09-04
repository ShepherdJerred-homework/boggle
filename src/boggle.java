import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

//        System.out.println(Arrays.deepToString(cube));

        for (String input : inputs) {
//            System.out.println(input);

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

            String output;
            if (result) {
                output = String.format("%-17.17s%-1s", input, "VALID");
            } else {
                output = String.format("%-17.17s%-1s", input, "NOT VALID");
            }
            System.out.println(output);
            printWriter.println(output);
        }
        printWriter.close();
    }

    public static boolean findString(char[] string, int currStringPos, int currRow, int currColumn, char[][] cube, boolean[][] usedChars) {
//        usedChars = copy(usedChars);

        if (currRow > 3 || currRow < 0 || currColumn > 3 || currColumn < 0) {
            return false;
        }

        if (usedChars[currRow][currColumn]) {
//            System.out.println(String.format("Skipping r %s  c %s", currRow, currColumn));
            return false;
        }

        char currentChar = string[currStringPos];

//        System.out.println(String.format("s %s  csp %s   r %s  c %s   cc %s", Arrays.toString(string), currStringPos, currRow, currColumn, currentChar));

        if (currentChar != cube[currRow][currColumn]) {
            return false;
        } else {
//            System.out.println(String.format("%s found at r %s c %s", currentChar, currRow, currColumn));
            if (currStringPos == string.length - 1) {
                return true;
            }
        }

        usedChars[currRow][currColumn] = true;

        for (int row = -1; row <= 1; row++) {
            for (int column = -1; column <= 1; column++) {
                if (row == 0 && column == 0) {
                    continue;
                }
                if (findString(string, currStringPos + 1, currRow + row, currColumn + column, cube, usedChars)) {
                    return true;
                }
            }
        }

        usedChars[currRow][currColumn] = false;

        return false;
    }
}

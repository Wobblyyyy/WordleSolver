package me.wobblyyyy.wordlesolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordleSolver {
    // 0, 1, 2, 3, 4: char must be at index
    // -1:            char can be anywhere
    // -2:            char can not be in word
    // -3:            no rules are applied for the char

    private static final String EXAMPLE = "example inputs:\n'a -2': " +
            "character 'a' may not be in the string\n'a -1': character 'a' " +
            "must be somewhere in the string\n'a 0': character 'a' must be " +
            "at index 0\n'b 4': character 'b' must be at index 4\n'm -3': " +
            "remove any restrictions placed on character 'm'";

    private static final Map<Character, Integer> reqs = new HashMap<>();

    private static void showSolutions() {
        WordList list = WordList.getAllWords();

        WordList solutions = list.filter((word) -> {
            for (Map.Entry<Character, Integer> entry : reqs.entrySet()) {
                char character = entry.getKey();
                int index = entry.getValue();

                if (index > -1) {
                    if (!word.charAtIndex(character, index))
                        return false;
                } else if (index == -1) {
                    if (!word.containsChar(character))
                        return false;
                } else {
                    if (!word.doesNotContainChar(character))
                        return false;
                }
            }

            return true;
        });

        System.out.println(solutions);
        System.out.printf(
                "found %s possible solutions (%s were displayed)%n> ",
                solutions.size(),
                Math.min(solutions.size(), 100)
        );
    }

    private static boolean parseString(String string) {
        if (string.length() == 0)
            return true;

        String[] split = string.split(" ");

        if (split.length != 2) {
            System.out.printf("invalid input <%s>!%ncould not parse " +
                    "input because the split array had a length " +
                    "not equal to 2. the split array was <%s>%n%s%n> ", string,
                    Arrays.toString(split), EXAMPLE);

            return false;
        }

        char character = split[0].charAt(0);
        int index = Integer.parseInt(split[1]);

        if (index < -3 || index > 4) {
            System.out.printf("invalid input <%s>!could not parse " +
                    "input because the index was invalid. the index must " +
                    "be greater than -4 and less than 5!%n%s%n> ",
                    string, EXAMPLE);

            return false;
        }

        reqs.put(character, index);

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine().replaceAll("\n", "");

            if (line.contains("exit"))
                break;

            if (parseString(line))
                showSolutions();
        }

        scanner.close();
    }
}

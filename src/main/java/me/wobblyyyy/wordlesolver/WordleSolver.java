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
            "remove any restrictions placed on character 'm'\n'whear -+++-':\n" +
            "  'w' is NOT in the word, 'h', 'e', and 'a' are all in the " +
            "correct places, and 'r' is NOT in the word\n'quirk ----- " +
            "heart ___-- whear -+++-':\n  none of the letters in 'quirk' are " +
            "in the word\n  'h', 'e', and 'a' are in the word " +
            "(wrong place)\n  'w' is not in the word but 'h'," +
            "'e', and 'a' are in the correct place, " +
            "'r' is not in the word";
    private static final String VALID_CHARS = "valid characters are:\n'_': " +
            "the letter was in last word but not in the right position\n" +
            "'+': the letter was in the last word and in the right " +
            "position\n'-': the letter was NOT in the last word at all";
    private static final String HELP = EXAMPLE + "\n" + VALID_CHARS + "\n" +
            "type 'HELP' for help\ntype 'EXIT' to exit\ntype 'RESET' to reset";
    private static final Map<Character, Integer> reqs = new HashMap<>();
    private static String lastWord = "";
    private static WordList lastWordList;

    private static void showSolutions() {
        long start = System.currentTimeMillis();
        WordList solutions = lastWordList.filter((word) -> {
            if (reqs.size() == 0)
                return true;

            for (Map.Entry<Character, Integer> entry : reqs.entrySet()) {
                char character = entry.getKey();
                int index = entry.getValue();

                if (index == -3) {
                    return true;
                } else if (index > -1) {
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
                "found %s possible solutions in %sms (%s were displayed)%n> ",
                solutions.size(),
                System.currentTimeMillis() - start,
                Math.min(solutions.size(), 100)
        );

        lastWordList = solutions;
    }

    private static boolean parseString(String string) {
        if (string.length() == 0)
            return true;

        String[] split = string.split(" ");

        if (split.length == 1) {
            String word = split[0];

            if (word.length() != 5) {
                System.out.printf("invalid input <%s>! could not parse " +
                        "input because the inputted word did not have " +
                        "a length of exactly 5 characters!%n%s%n> ",
                        string, VALID_CHARS);

                return false;
            }

            if (Character.isAlphabetic(word.codePointAt(0))) {
                lastWord = word;

                return true;
            } else {
                for (int i = 0; i < lastWord.length(); i++) {
                    char c = lastWord.charAt(i);

                    switch (word.charAt(i)) {
                        case '_':
                            reqs.put(c, -1); // somewhere in the word
                            break;
                        case '+':
                            reqs.put(c, i); // in a specific place
                            break;
                        case '-':
                            reqs.put(c, -2); // not in the word
                            break;
                        default:
                            System.out.printf("invalid input <%s>! could " +
                                    "not parse input because there was an " +
                                    "unrecognized character '%s'.%n%s%n> ",
                                    string, c, VALID_CHARS);

                            return false;
                    }
                }

                return true;
            }
        }

        if (split.length > 2 && split.length % 2 == 1) {
            System.out.printf("invalid input <%s>!%ncould not parse " +
                    "input because the split array had a length " +
                    "not equal to 2. the split array was <%s>%n%s%n> ", string,
                    Arrays.toString(split), EXAMPLE);

            return false;
        }

        if (split[0].length() == 5) {
            for (int i = 0; i < split.length; i += 2)
                if (!(parseString(split[i]) && parseString(split[i + 1])))
                    return false;

            return true;
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

        System.out.println("loading...");
        long start = System.currentTimeMillis();
        lastWordList = WordList.getAllWords();
        int size = lastWordList.size();
        long elapsed = System.currentTimeMillis() - start;
        showSolutions();
        System.out.printf("loaded %s words in %sms (%sms per word)%n",
                size, elapsed, elapsed / size);
        System.out.println(">>> HELP <<<");
        System.out.println(HELP);

        while (true) {
            String line = scanner.nextLine().replaceAll("\n", "");

            if (line.contains("HELP") || line.equalsIgnoreCase("help")) {
                System.out.println(">>> HELP <<<");
                System.out.println(HELP);
            } else if (line.contains("EXIT") || line.equalsIgnoreCase("exit")) {
                System.out.println("well... bye... i guess...");
                break;
            } else if (line.contains("RESET") || line.equalsIgnoreCase("RESET")) {
                System.out.print("reset solver!\n> ");
                reqs.clear();
                lastWordList = WordList.getAllWords();
            } else if (parseString(line)) {
                showSolutions();
            }
        }

        scanner.close();
    }
}

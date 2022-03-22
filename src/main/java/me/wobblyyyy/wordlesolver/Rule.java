package me.wobblyyyy.wordlesolver;

public class Rule {
    private final char letter;
    private final Position position;

    public Rule(char letter, Position position) {
        this.letter = letter;
        this.position = position;
    }

    public boolean matches(String word) {
        char[] chars = word.toCharArray();
        int length = chars.length;

        switch (position) {
            case NOT_IN_WORD:
                for (int i = 0; i < length; i++) {
                    if (chars[i] == letter) {
                        return false;
                    }
                }
                return true;
            case IN_WORD:
                for (int i = 0; i < length; i++) {
                    if (chars[i] == letter) {
                        return true;
                    }
                }
                return false;
            case INDEX_1:
            case INDEX_2:
            case INDEX_3:
            case INDEX_4:
            case INDEX_5:
                return chars[Position.toInt(position)] == letter;
            default: throw new RuntimeException();
        }
    }

    public boolean hasLetter(char letter) {
        return this.letter == letter;
    }
}

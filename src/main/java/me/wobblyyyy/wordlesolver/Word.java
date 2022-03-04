package me.wobblyyyy.wordlesolver;

public class Word {
    private final String string;
    private final char[] chars;

    public Word(String string) {
        this.string = string;
        this.chars = string.toCharArray();
    }

    public String getString() {
        return string;
    }

    public boolean containsChar(char c) {
        for (char a : chars)
            if (c == a)
                return true;

        return false;
    }

    public boolean doesNotContainChar(char c) {
        for (char a : chars)
            if (c == a)
                return false;

        return true;
    }

    public boolean charAtIndex(char c,
                               int index) {
        return chars[index] == c;
    }

    @Override
    public String toString() {
        return string;
    }
}

package me.wobblyyyy.wordlesolver;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

public class WordList extends ArrayList<Word> {
    private static WordList allWords;

    private static void loadWords() {
        Scanner scanner = new Scanner(
                WordList.class.getResourceAsStream("/words.txt"),
                "UTF-8"
        ).useDelimiter("\\A");

        String str = scanner.next();
        String[] words = str.split("\n");

        allWords = new WordList(words.length);

        for (String word : words)
            allWords.add(new Word(word));

        scanner.close();
    }

    public static WordList getAllWords() {
        if (allWords == null)
            loadWords();

        return allWords;
    }

    public WordList() {
        this(10);
    }

    public WordList(int size) {
        super(size);
    }

    public WordList filter(Predicate<Word> predicate) {
        WordList list = new WordList(this.size());

        for (Word word : this)
            if (predicate.test(word))
                list.add(word);

        return list;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(600);

        int size = Math.min(size(), 100);

        for (int i = 0; i < size; i++) {
            builder.append(get(i));
            builder.append("\n");
        }

        builder.setLength(builder.length() - 1);

        return builder.toString();
    }
}

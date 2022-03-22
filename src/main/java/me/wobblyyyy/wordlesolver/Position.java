package me.wobblyyyy.wordlesolver;

public enum Position {
    NOT_IN_WORD,
    IN_WORD,
    INDEX_1,
    INDEX_2,
    INDEX_3,
    INDEX_4,
    INDEX_5;

    static int toInt(Position position) {
        switch (position) {
            case INDEX_1: return 0;
            case INDEX_2: return 1;
            case INDEX_3: return 2;
            case INDEX_4: return 3;
            case INDEX_5: return 4;
            default: throw new RuntimeException();
        }
    }

    static Position toPosition(int i) {
        switch (i) {
            case 0: return INDEX_1;
            case 1: return INDEX_2;
            case 2: return INDEX_3;
            case 3: return INDEX_4;
            case 4: return INDEX_5;
            default: throw new RuntimeException();
        }
    } 
}

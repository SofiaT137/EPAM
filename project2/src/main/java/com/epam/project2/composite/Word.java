package com.epam.project2.composite;

public class Word extends Composite {

    private String word;

    public Word(String word) {
        this.word = word;
        this.type = Parts.WORD;
    }

    @Override
    public String toString() {
        return word;
    }

    public String getWord() {
        return word;
    }
}

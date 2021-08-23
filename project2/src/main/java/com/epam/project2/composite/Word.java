package com.epam.project2.composite;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return getWord().equals(word1.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord());
    }
}

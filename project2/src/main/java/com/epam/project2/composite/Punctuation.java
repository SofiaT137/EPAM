package com.epam.project2.composite;

import java.util.Objects;

public class Punctuation extends Composite {

    private String item;

    public Punctuation(String item){
        this.item = item;
        this.type = Parts.PUNCTUATION;
    }

    @Override
    public String toString() {
        return item;
    }

    public String getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Punctuation)) return false;
        Punctuation that = (Punctuation) o;
        return getItem().equals(that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItem());
    }
}

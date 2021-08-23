package com.epam.project2.composite;

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
}

package com.epam.project2.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sentence extends Composite implements Comparable<Sentence> {

    private int countWords;

    public Sentence() {
        this.list = new ArrayList<Composite>();
        this.type = Parts.SENTENCE;
        countWords = 0;
    }

    @Override
    public List<Composite> getList() {
        return list;
    }

    public void add(Composite composite) {
        list.add(composite);
        if (composite.getType() == Parts.WORD){
           this.countWords++;
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (list.size() > 0) {
            sb.append(list.get(0));
        }
        for (int i = 1; i < list.size(); ++i) {
            if (list.get(i).type == Parts.WORD) {
                if (sb.charAt(sb.length() - 1) != '-') {
                    sb.append(" ");
                }
            }
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Sentence o) {
        return this.countWords - o.countWords;
    }
}

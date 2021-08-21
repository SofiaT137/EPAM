package com.epam.project2.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sentence extends Composite {

    public Sentence() {
        this.list = new ArrayList<Composite>();
        this.type = Parts.SENTENCE;
    }

    @Override
    public List<Composite> getList() {
        return list;
    }

    public void add(Composite composite) {
        list.add(composite);
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
}

package com.epam.jwd.project3.model.composite;

import com.epam.jwd.project3.model.composite.Composite;

import java.util.ArrayList;
import java.util.List;

public class Shelf extends Composite {

    public Shelf(String shelfName){
        this.name = shelfName;
        this.list = new ArrayList<>();
    }

    @Override
    public List<Composite> getList() {
        return list;
    }

    public void add(Composite composite){
        list.add(composite);
    }

    public void setShellName(String shelfName) {
        this.name = shelfName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Composite composite : list) {
            sb.append(composite).append("\n");
        }

        return sb.toString();
    }
}

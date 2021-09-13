package com.epam.jwd.project3.model;

import java.util.ArrayList;
import java.util.List;

public class Library extends Composite{

    public Library(String libraryName){
        this.name = libraryName;
        this.list = new ArrayList<Composite>();
    }


    @Override
    public List<Composite> getList() {
        return list;
    }



    public void add(Composite composite){
        list.add(composite);
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

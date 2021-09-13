package com.epam.jwd.project3.model;

import java.util.ArrayList;
import java.util.List;

public class Shell extends Composite{

    public Shell(String shellName){
        this.name = shellName;
        this.list = new ArrayList<>();
    }

    @Override
    public List<Composite> getList() {
        return list;
    }

    public void add(Composite composite){
        list.add(composite);
    }

    public void setShellName(String shellName) {
        this.name = shellName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Composite composite : list) {
            sb.append(composite).append(" ");
        }

        return sb.toString();
    }
}

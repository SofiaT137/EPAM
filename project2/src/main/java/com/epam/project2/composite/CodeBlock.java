package com.epam.project2.composite;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock extends Composite{

       public CodeBlock(){
           this.list = new ArrayList<Composite>();
           this.type = Parts.CODEBLOCK;
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
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}

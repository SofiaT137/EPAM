package com.epam.project2.composite;

import java.util.List;

public class Composite {

    protected Parts type;
    protected List<Composite> list;

    public enum Parts{
       TEXT,
        CODEBLOCK,
        CODELINE,
        TEXTBLOCK,
        SENTENCE,
        WORD,
        PUNCTUATION
    }

    public List<Composite> getList(){
        return null;
    }
    public Parts getType(){
        return this.type;
    }
}

package com.epam.project2.parser;

import com.epam.project2.composite.Composite;
import com.epam.project2.utils.MyException;

import java.util.List;

public abstract class ParseHandler {
    public ParseHandler nextParse;

    public ParseHandler(ParseHandler nextParser) {
        this.nextParse = nextParser;
    }

    public ParseHandler() {
        this.nextParse = null;
    }

    public abstract Composite parse(List<String> data, Composite.Parts type) throws MyException;
}

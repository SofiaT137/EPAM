package com.epam.project2.parser;

public class ParseText extends ParseHandler{
    public ParseText(ParseHandler nextParser) {
        super(nextParser);
    }

    @Override
    public boolean isParsed() {
        return false;
    }
}

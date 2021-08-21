package com.epam.project2.parser;

import com.epam.project2.composite.CodeBlock;
import com.epam.project2.composite.CodeLine;
import com.epam.project2.composite.Composite;

import java.util.List;

public class ParseCodeBlock extends ParseHandler{

    public ParseCodeBlock(ParseHandler nextParser) {
        super(nextParser);
    }

    @Override
    public Composite parse(List<String> data, Composite.Parts type) {
        if (type == Composite.Parts.CODEBLOCK) {
            CodeBlock codeBlock = new CodeBlock();
            for (String s :
                    data) {
                codeBlock.add(new CodeLine(s));
            }
            return codeBlock;

        }else if(this.nextParse != null){
            return this.nextParse.parse(data,type);
        }
        //TODO throw my exception and it add in log
        return null;
    }
}

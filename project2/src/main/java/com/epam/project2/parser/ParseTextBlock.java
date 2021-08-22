package com.epam.project2.parser;

import com.epam.project2.composite.Composite;
import com.epam.project2.composite.TextBlock;
import com.epam.project2.utils.MyException;

import java.util.ArrayList;
import java.util.List;

public class ParseTextBlock extends ParseHandler{

    public ParseTextBlock(ParseHandler nextParser) {
        super(nextParser);
    }

    public static final String SENTENCE = "((?<=[.!?]))";

    @Override
    public Composite parse(List<String> data, Composite.Parts type) throws MyException {
        if(type == Composite.Parts.TEXTBLOCK){
            TextBlock textBlock = new TextBlock();
            List<String> sentence = new ArrayList<>();
            String line = data.get(0);
            String[] sentences = line.split(SENTENCE);
            for (String sent :
                    sentences) {
                sentence.add(sent);
                textBlock.add(nextParse.parse(sentence, Composite.Parts.SENTENCE));
                sentence.remove(0);
            }

        return textBlock;
        }else if(this.nextParse != null){
            return this.nextParse.parse(data,type);
        }
        throw new MyException("I can't parse text block with type  = " + type);
    }

}

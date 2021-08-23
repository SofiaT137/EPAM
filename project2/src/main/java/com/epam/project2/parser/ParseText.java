package com.epam.project2.parser;

import com.epam.project2.composite.CodeBlock;
import com.epam.project2.composite.Composite;
import com.epam.project2.composite.Text;
import com.epam.project2.exception.MyException;

import java.util.ArrayList;
import java.util.List;

public class ParseText extends ParseHandler {

    public ParseText(ParseHandler nextParser) {
        super(nextParser);
    }

    @Override
    public Composite parse(List<String> data, Composite.Parts type) throws MyException {
        if (type == Composite.Parts.TEXT) {
            Text text = new Text();
            List<String> block = null;
            boolean isCode = false;
            int count = 0;
            CodeBlock codeBlock = null;

            for (String s : data) {
                if (s.contains("{")) {
                    if (count == 0) {
                        isCode = true;
                        block = new ArrayList<>();
                    }
                    count++;
                }
                if (s.contains("}")) {
                    count--;
                }
                if (!isCode) {
                    block = new ArrayList<>();
                    block.add(s);
                    text.add(nextParse.parse(block, Composite.Parts.TEXTBLOCK));
                } else {
                    block.add(s);
                    if (count == 0) {
                        text.add(nextParse.parse(block, Composite.Parts.CODEBLOCK));
                        isCode = false;
                    }
                }
            }
            return text;

        }else if(this.nextParse != null){
            return this.nextParse.parse(data,type);
        }
        throw new MyException("I can't parse text with type  = " + type);
    }
}

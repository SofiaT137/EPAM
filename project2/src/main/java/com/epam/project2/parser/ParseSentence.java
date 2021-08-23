package com.epam.project2.parser;


import com.epam.project2.composite.Composite;
import com.epam.project2.composite.Punctuation;
import com.epam.project2.composite.Sentence;
import com.epam.project2.composite.Word;

import java.util.List;

public class ParseSentence extends ParseHandler {

    public ParseSentence(ParseHandler nextParser) {
        super(nextParser);
    }

    public static final String WORDS = "[\\t\\s]";
    public static final String WORD_PUNCTUATION = "((?=[._^%$#?!~@+,-:;])|(?<=[._^%$#!~@?+,-:;]))";
    public static final String PUNCTUATION = "[._^%$#?!~@+,-:;]";

    @Override
    public Composite parse(List<String> data, Composite.Parts type) throws MyException {
        if(type == Composite.Parts.SENTENCE){
            Sentence sentence = new Sentence();
            String line = data.get(0);
            String[] sentenceParts = line.split(WORDS);
            for (String sentencePart : sentenceParts) {
                if (sentencePart.length() != 0) {
                    String[] words = sentencePart.split(WORD_PUNCTUATION);
                    for (String word : words) {
                        if (word.matches(PUNCTUATION)) {
                            sentence.add(new Punctuation(word));
                        } else {
                            sentence.add(new Word(word));
                        }
                    }
                }
            }
            return sentence;
        }else if(this.nextParse != null){
            return this.nextParse.parse(data,type);
        }

        throw new MyException("I can't parse sentence with type  = " + type);
    }
}

package com.epam.project2.parser;

import com.epam.project2.composite.*;

import java.util.List;

public class TextParser {

    protected List<String> listOfTextStrings;

    public static final String SENTENCE = "((?<=[.!?]))";
    public static final String WORDS = "[\\t\\s]";
    public static final String WORD_PUNCTUATION = "((?=[._^%$#?!~@+,-:;])|(?<=[._^%$#!~@?+,-:;]))";
    public static final String PUNCTUATION = "[._^%$#?!~@+,-:;]";

    public TextParser(List<String> list) {
        listOfTextStrings = list;
    }

    public Text textSeparation() {

        Text text = new Text();
        boolean isCode = false;
        int count = 0;
        CodeBlock codeBlock = null;

        for (String s : this.listOfTextStrings) {

            if(s.contains("{")){
                if (count == 0){
                    isCode = true;
                    codeBlock = new CodeBlock();
                }
                count++;
            }

            if(s.contains("}")) {
                count--;
            }

            if(!isCode){
                text.add(textBlockSeparation(s));
            }
            else{
               codeBlock.add(new CodeLine(s));
                if (count == 0) {
                    text.add(codeBlock);
                    isCode = false;
                }
            }
        }

        return text;
    }

    public TextBlock textBlockSeparation(String str){
        TextBlock textBlock = new TextBlock();
        String[] sentences = str.split(SENTENCE);
        for (String s :
                sentences) {
            textBlock.add(sentenceSeparation(s));
        }
        return textBlock;
    }

    public Sentence sentenceSeparation(String sentence){
      Sentence sentence1 = new Sentence();
      String[] sentenceParts = sentence.split(WORDS);
        for (String sentencePart : sentenceParts) {
            if (sentencePart.length() != 0){
                String[] words = sentencePart.split(WORD_PUNCTUATION);
                for (String word : words) {
                    if(word.matches(PUNCTUATION)){
                        sentence1.add(new Punctuation(word));
                    }
                    else{
                        sentence1.add(new Word(word));
                    }
                }
            }
        }
      return sentence1;
    }
}
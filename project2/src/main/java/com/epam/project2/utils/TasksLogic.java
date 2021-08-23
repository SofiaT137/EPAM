package com.epam.project2.utils;


import com.epam.project2.composite.Composite;
import com.epam.project2.composite.Sentence;
import com.epam.project2.composite.Text;
import com.epam.project2.composite.Word;
import com.epam.project2.composite.Punctuation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TasksLogic {

    Text text;

    public TasksLogic(Text text){
        this.text = text;
    }

    public int firstTaskLogic(){
        List<Composite> listOfBlocks = text.getList();
        HashMap<Word, Integer> mapOfWords = new HashMap<>();
        for (Composite block: listOfBlocks) {
            if (block.getType() == Composite.Parts.TEXTBLOCK){
                for (Composite sentence : block.getList()) {
                    HashSet<Word> setOfWords = new HashSet<>();
                    for (Composite part : sentence.getList()) {
                      if (part.getType() == Composite.Parts.WORD){
                          setOfWords.add((Word)part);
                      }
                    }
                    for (Word word : setOfWords) {
                        if (mapOfWords.containsKey(word)){
                            mapOfWords.put(word,mapOfWords.get(word)+1);
                        } else {
                            mapOfWords.put(word,1);
                        }
                    }
                }
            }
        }
        int count = 0;
        for (Map.Entry<Word, Integer> stringIntegerEntry : mapOfWords.entrySet()) {
            count = Math.max(count,stringIntegerEntry.getValue());
        }
        return count;
    }

    public void secondTaskLogic(){
        List<Sentence> sorted= new ArrayList<>();
        for (Composite block: text.getList()) {
            if (block.getType() == Composite.Parts.TEXTBLOCK) {
                for (Composite sentence : block.getList()) {
                    sorted.add((Sentence)sentence);
                }
            }
        }
        Collections.sort(sorted);
        for (Sentence sentence : sorted) {
            System.out.println(sentence);
        }
    }

    public void forthTaskLogic(int length) {
        HashSet<Word> setOfWords = new HashSet<>();
        for (Composite block : text.getList()) {
            if (block.getType() != Composite.Parts.TEXTBLOCK) {
                continue;
            }
            for (Composite sentence : block.getList()) {
                List<Composite> sentencePart = sentence.getList();
                if (sentence.getList().size() <= 1) {
                    continue;
                }
                Composite lastPart = sentencePart.get(sentencePart.size() - 1);
                if (lastPart.getType() == Composite.Parts.PUNCTUATION) {
                    Punctuation lastChar = (Punctuation) lastPart;
                    if (lastChar.getItem().equals("?")) {
                        for (Composite part : sentencePart) {
                            if (part.getType() == Composite.Parts.WORD && ((Word) part).getWord().length() == length) {
                                setOfWords.add((Word) part);
                            }
                        }
                    }
                }
            }
        }
        for (Word setOfWord : setOfWords) {
            System.out.println(setOfWord);
        }
    }
}

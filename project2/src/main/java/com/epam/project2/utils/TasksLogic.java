package com.epam.project2.utils;

import com.epam.project2.composite.Composite;
import com.epam.project2.composite.Text;
import com.epam.project2.composite.Word;

import java.util.*;

public class TasksLogic {

    Text text;

    public TasksLogic(Text text){
        this.text = text;
    }

    public int firstTaskLogic(){
        List<Composite> listOfBlocks = text.getList();
        HashMap<String,Integer> mapOfWords = new HashMap<>();
        for (Composite block: listOfBlocks) {
            if (block.getType() == Composite.Parts.TEXTBLOCK){
                for (Composite sentence : block.getList()) {
                    HashSet<String> setOfWords = new HashSet<>();
                    for (Composite part : sentence.getList()) {
                      if (part.getType() == Composite.Parts.WORD){
                          setOfWords.add(((Word)part).getWord());
                      }
                    }
                    for (String word : setOfWords) {
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
        for (Map.Entry<String, Integer> stringIntegerEntry : mapOfWords.entrySet()) {
            count = Math.max(count,stringIntegerEntry.getValue());
        }
        return count;
    }


}

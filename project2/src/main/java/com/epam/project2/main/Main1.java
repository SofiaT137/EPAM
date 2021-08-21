package com.epam.project2.main;

import com.epam.project2.composite.Sentence;
import com.epam.project2.composite.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main1
{
    public static final String WORD = "\\b.*[._^%$#!~@,-:;]\\B";
    public static final String PUNCTUATION = "((?=[._^%$#?!~@+,-:;])|(?<=[._^%$#!~@?+,-:;]))";
    static String str = " The   \t if-then statement is the most basic, of all the control flow statements. It tells your program to execute a certain section of code only if a particular test evaluates to true. For example, the Bicycle class could allow the brakes to decrease the bicycle's speed only if the bicycle is already in motion. One possible implementation of the applyBrakes method could be as follows:";

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String mass2[] = str.split("((?<=[.!?]))");

        String[] mass3 = mass2[0].split("[\\t\\s]");
        for (String s : mass3) {
            if (s.length() != 0){
                String[] mass4 = s.split(PUNCTUATION);
                for (String s1 : mass4) {
                    if(s1.matches("[._^%$#?!~@+,-:;]")){
                        System.out.println(s1);
                    }
                }
            }
        }
    }
}

package com.epam.project2.main;

import com.epam.project2.composite.Text;
import com.epam.project2.parser.TextParser;
import com.epam.project2.utils.Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        File file = new File(reader.readLine());

        List<String> list = Reader.getTextLine(file);

        TextParser textParser = new TextParser(list);
        Text text = textParser.textSeparation();

        System.out.println(text);



    }
}

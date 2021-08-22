package com.epam.project2.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    private static final Logger logger = LogManager.getLogger(Reader.class.getName());

    public static List<String> getTextLine (File inputFile){
        List<String> textLines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            while (reader.ready()){
                textLines.add(reader.readLine());
            }
        }catch (SecurityException | IOException exception){
            logger.error(exception.getMessage());
        }
        return textLines;
    }

}
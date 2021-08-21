package com.epam.project2.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Reader {

    private static final Logger logger = Logger.getLogger(Reader.class.getName());

    public static List<String> getTextLine (File file){
        List<String> textLines = new ArrayList<>();
        try{
            textLines = Files.readAllLines(Path.of(file.getPath()), StandardCharsets.UTF_8);
        }catch (IOException|SecurityException exception){
            logger.info(exception.getMessage());
        }
        return textLines;
    }

}
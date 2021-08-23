package com.epam.project2.writer;

import com.epam.project2.composite.Text;
import com.epam.project2.reader.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    private static final Logger logger = LogManager.getLogger(Reader.class.getName());

    public static void writeTextLine(Text text, File outputFile){

       try(FileWriter writer = new FileWriter(String.valueOf(outputFile))) {
           writer.write(String.valueOf(text));
       } catch (IOException error) {
           logger.error(error.getMessage());
       }
    }

}

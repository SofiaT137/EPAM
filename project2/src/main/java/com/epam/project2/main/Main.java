package com.epam.project2.main;

import com.epam.project2.composite.Composite;
import com.epam.project2.composite.Text;
import com.epam.project2.parser.ParseCodeBlock;
import com.epam.project2.parser.ParseSentence;
import com.epam.project2.parser.ParseText;
import com.epam.project2.parser.ParseTextBlock;
import com.epam.project2.utils.MyException;
import com.epam.project2.utils.Reader;
import com.epam.project2.utils.TaskOneLogic;
import com.epam.project2.utils.TasksLogic;
import com.epam.project2.utils.Writer;

import java.io.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            File file = new File(reader.readLine());
            List<String> list = Reader.getTextLine(file);

            ParseSentence parseSentence = new ParseSentence(null);
            ParseTextBlock parseTextBlock = new ParseTextBlock(parseSentence);
            ParseCodeBlock parseCodeBlock = new ParseCodeBlock(parseTextBlock);
            ParseText parseText = new ParseText(parseCodeBlock);

            Text text = (Text) parseText.parse(list, Composite.Parts.TEXT);
            File outFile = new File(reader.readLine());
            Writer.writeTextLine(text, outFile);
            System.out.println(new TasksLogic(text).firstTaskLogic());
        }
        catch (IOException e){
            logger.fatal(e.getMessage());
        } catch (MyException e){
            logger.error("My exeption: " + e.getMessage());
        }

    }
}

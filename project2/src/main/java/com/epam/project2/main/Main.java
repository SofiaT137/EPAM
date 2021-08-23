package com.epam.project2.main;

import com.epam.project2.composite.Composite;
import com.epam.project2.composite.Text;
import com.epam.project2.parser.ParseCodeBlock;
import com.epam.project2.parser.ParseSentence;
import com.epam.project2.parser.ParseText;
import com.epam.project2.parser.ParseTextBlock;
import com.epam.project2.exception.MyException;
import com.epam.project2.reader.Reader;
import com.epam.project2.logic.TasksLogic;
import com.epam.project2.writer.Writer;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please, enter the path of input file (example, D:\\example1.txt)");
            File file = new File(reader.readLine());
            List<String> list = Reader.getTextLine(file);

            ParseSentence parseSentence = new ParseSentence(null);
            ParseTextBlock parseTextBlock = new ParseTextBlock(parseSentence);
            ParseCodeBlock parseCodeBlock = new ParseCodeBlock(parseTextBlock);
            ParseText parseText = new ParseText(parseCodeBlock);

            Text text = (Text) parseText.parse(list, Composite.Parts.TEXT);
            System.out.println("Please, enter the path of output file (example, D:\\example2.txt)");
            File outFile = new File(reader.readLine());
            Writer.writeTextLine(text, outFile);

            TasksLogic tasksLogic = new TasksLogic(text);
            System.out.println("First task: ");
            System.out.println(tasksLogic.firstTaskLogic());
            System.out.println("Second task: ");
            tasksLogic.secondTaskLogic();
            System.out.println("Forth task: ");
            System.out.println("Please, enter the wished number of letters: ");
            int number = Integer.parseInt(reader.readLine());
            tasksLogic.forthTaskLogic(number);
        }
        catch (IOException e){
            logger.fatal(e.getMessage());
        } catch (MyException e){
            logger.error("My exception: " + e.getMessage());
        }
    }
}

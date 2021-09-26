package com.epam.jwd.project3.controller;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Exchanger;

public class ExchangeBookController implements Runnable{

    private static final Logger logger = LogManager.getLogger(ExchangeBookController.class.getName());
    private static final String METHOD_RUN_START = "Method run from ExchangeBookController started ";
    private static final String METHOD_RUN_FINISHED = "Method run from ExchangeBookController finished ";

    Exchanger<Book> exchanger;
    Book book;
    User user;

    public ExchangeBookController(Exchanger<Book> exchanger,Book book,User user) {
        this.exchanger = exchanger;
        this.book = book;
        this.user = user;
    }

    @Override
    public void run() {
        try {
            logger.info(Thread.currentThread().getId() + ": "+ METHOD_RUN_START);
            user.getReaderShelf().remove(book);
            book = exchanger.exchange(book);
            user.getReaderShelf().add(book);
        // TODO Nice to add returnBookController
        } catch (InterruptedException e) {
            logger.error(String.valueOf(Thread.currentThread().getId()) + ": "+ e.getMessage());
            Thread.currentThread().interrupt();
        }
        logger.info(Thread.currentThread().getId() + ": "+ METHOD_RUN_FINISHED);
    }

}

package com.epam.jwd.project3.controller;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.user.User;
import com.epam.jwd.project3.service.api.BookService;
import com.epam.jwd.project3.service.api.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;

public class ReturnBookController implements Runnable{

    String name;
    Book book;
    UserService userService;
    BookService bookService;
    long seconds;

    private static final int CONVERTER = 1000;
    private static final Logger logger = LogManager.getLogger(ReturnBookController.class.getName());
    private static final String METHOD_RUN_START = "Method run from ReturnBookController started ";
    private static final String METHOD_RUN_FINISHED = "Method run from ReturnBookController finished ";

    public ReturnBookController(String name, Book book, UserService userService, BookService bookService, long seconds) {
        this.name = name;
        this.book = book;
        this.userService = userService;
        this.bookService = bookService;
        this.seconds = seconds;
    }

    @Override
    public void run() {
        try {
            logger.info(Thread.currentThread().getId() + ": "+ METHOD_RUN_START);
            Thread.sleep(seconds * CONVERTER);
            User user = userService.signIn(name);
            userService.returnTheBook(book);
            bookService.returnBookToLibrary(book);
            userService.signOut();
        } catch (InterruptedException e) {
            logger.error(String.valueOf(Thread.currentThread().getId()) + ": "+ e.getMessage());
            userService.signOut();
            Thread.currentThread().interrupt();
        } catch (NoSuchElementException e) {
            logger.error(String.valueOf(Thread.currentThread().getId()) + ": Can't return the book ("+ e.getMessage() + ")");
            userService.signOut();
            Thread.currentThread().interrupt();
        } catch (NullPointerException e) {
            logger.error(String.valueOf(Thread.currentThread().getId()) + ": Can't sign in user ("+ e.getMessage() + ")");
            Thread.currentThread().interrupt();
        }

        logger.info(Thread.currentThread().getId() + ": "+ METHOD_RUN_FINISHED);
    }
}

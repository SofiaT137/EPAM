package com.epam.jwd.project3.controller;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.user.User;
import com.epam.jwd.project3.service.api.BookService;
import com.epam.jwd.project3.service.api.UserService;
import com.epam.jwd.project3.view.impl.UserMenuImpl;

import java.util.NoSuchElementException;

public class ReturnBookController implements Runnable{

    String name;
    Book book;
    UserService userService;
    BookService bookService;
    long seconds;

    private static final int CONVERTER = 1000;

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
            Thread.sleep(seconds*CONVERTER);
            User user = userService.signIn(name);
            userService.returnTheBook(book);
            bookService.returnBookToLibrary(book);
            userService.signOut();
        } catch (InterruptedException e) {
//          logger.error(e.);
            Thread.currentThread().interrupt();
        }catch (NoSuchElementException e) {
//             logger.error(e.getMessage());
//            Thread.currentThread().interrupt();
        }
        catch (NullPointerException e) {
//             logger.error(e.getMessage());
//            Thread.currentThread().interrupt();
        }
    }
}

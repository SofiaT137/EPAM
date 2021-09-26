package com.epam.jwd.project3.controller;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.composite.Library;
import com.epam.jwd.project3.model.user.User;
import com.epam.jwd.project3.repository.impl.UserRepositoryImpl;
import com.epam.jwd.project3.service.api.BookService;
import com.epam.jwd.project3.service.api.UserService;
import com.epam.jwd.project3.service.exception.FullReaderShelfException;
import com.epam.jwd.project3.service.impl.BookServiceImpl;
import com.epam.jwd.project3.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private BookService bookService;
    private UserRepositoryImpl userRepository;
    private UserService userService;

    private static final Logger logger = LogManager.getLogger(ControllerTest.class.getName());



    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(new Library("TEST"));
        bookService.createGoBooksList();
        bookService.createJavaBooksList();
        bookService.createJavaScriptBooksList();
        bookService.createRubyBooksList();
        bookService.createPythonBooksList();

        Semaphore semaphore = new Semaphore(1);
        userRepository = new UserRepositoryImpl();
        userService = new UserServiceImpl(userRepository,semaphore);
    }

    @Test
    void returnOneBook() throws InterruptedException, FullReaderShelfException {
        User user = new User("Sofia", false);
        userService.registration(user);
        user = userService.signIn(user.getName());

        Book book = bookService.getBookFromLibrary(0, 0);
        userService.takeTheBook(book);

        ReturnBookController returnBookController = new ReturnBookController(user.getName(),book,userService,bookService,1);
        new Thread(returnBookController).start();

        assertEquals(1,user.getReaderShelfSize());
        userService.signOut();

        synchronized (this) {
            wait(500);
        }
        user = userService.signIn(user.getName());
        assertEquals(1,user.getReaderShelfSize());
        userService.signOut();

        synchronized (this) {
            wait(1000);
        }
        user = userService.signIn(user.getName());
        assertEquals(0,user.getReaderShelfSize());
        userService.signOut();
    }

    @Test
    void returnBooks() throws InterruptedException, FullReaderShelfException {
        User user = new User("Sofia", false);
        userService.registration(user);
        user = userService.signIn(user.getName());

        Book book1 = bookService.getBookFromLibrary(0, 0);
        userService.takeTheBook(book1);


        ReturnBookController returnBookController1 = new ReturnBookController(user.getName(),book1,userService,bookService,1);
        new Thread(returnBookController1).start();

        Book book2 = bookService.getBookFromLibrary(0,1);
        userService.takeTheBook(book2);

        ReturnBookController returnBookController2 = new ReturnBookController(user.getName(),book2,userService,bookService,2);
        new Thread(returnBookController2).start();

        Book book3 = bookService.getBookFromLibrary(2, 0);
        boolean has_exception = false;
        try{
            userService.takeTheBook(book3);
        }catch (FullReaderShelfException e){
            has_exception = true;
            assertEquals(e.getMessage(),"No place to take another book. Please,return some books back!");
        }

        assertEquals(has_exception, true);

        assertEquals(2,user.getReaderShelfSize());
        userService.signOut();

        user = userService.signIn(user.getName());
        Book book = user.getReaderShelf().get(0);
        userService.returnTheBook(book);
        bookService.returnBookToLibrary(book);
        userService.signOut();

        synchronized (this) {
            wait(500);
        }

        user = userService.signIn(user.getName());
        assertEquals(1,user.getReaderShelfSize());
        userService.signOut();

        synchronized (this) {
            wait(2000);
        }

        user = userService.signIn(user.getName());
        assertEquals(0,user.getReaderShelfSize());
        userService.signOut();
    }

    @Test
    void exchangeBooks() throws InterruptedException, FullReaderShelfException {
        Exchanger<Book> exchanger = new Exchanger<>();
        User user1 = new User("Sofia", false);
        userService.registration(user1);
        user1 = userService.signIn(user1.getName());

        Book book1 = bookService.getBookFromLibrary(0, 0);
        userService.takeTheBook(book1);

        Book book2 = bookService.getBookFromLibrary(0, 1);
        userService.takeTheBook(book2);

        userService.signOut();

        User user2 = new User("Alex", false);
        userService.registration(user2);
        user2 = userService.signIn(user2.getName());

        Book book3 = bookService.getBookFromLibrary(4, 0);
        userService.takeTheBook(book3);

        Book book4 = bookService.getBookFromLibrary(4, 1);
        userService.takeTheBook(book4);

        userService.signOut();

        new Thread( new ExchangeBookController(exchanger,book1,user1)).start();
        new Thread(new ExchangeBookController(exchanger,book3,user2)).start();

        synchronized (this) {
            // TODO add protection for shared memory
            wait(5000);
        }
        userService.signIn(user1.getName());

        List<Book> books = user1.getReaderShelf();

        assertEquals(book3,books.get(1));

        userService.signOut();

    }

    @AfterEach
    void tearDown() {
    }
}
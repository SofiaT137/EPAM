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
import com.epam.jwd.project3.view.api.UserMenu;
import com.epam.jwd.project3.view.impl.UserMenuImpl;
import com.epam.jwd.project3.view.validator.UserMenuValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class Controller {

    private static final Logger logger = LogManager.getLogger(ReturnBookController.class.getName());
    private static final String LIBRARY_NAME = "Sofia's library";
    private static final String LIBRARY_CREATED = "The library has been created.";

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("The main thread is started");
        BookService bookService = new BookServiceImpl(new Library(LIBRARY_NAME));
        logger.info(LIBRARY_NAME);
        User currentUser = null;

        bookService.createGoBooksList();
        bookService.createJavaBooksList();
        bookService.createJavaScriptBooksList();
        bookService.createRubyBooksList();
        bookService.createPythonBooksList();

        logger.info(LIBRARY_CREATED);

        Semaphore semaphore = new Semaphore(1);
        Exchanger<Book> exchanger = new Exchanger<>();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository,semaphore);
        UserMenu userMenu = new UserMenuValidator(new UserMenuImpl(),userRepository,bookService);

        while (true) {

            userMenu.getStartMenu();
            int userChoice = userMenu.getRequiredNumber();

            if (userChoice == 1) {
                String name = userMenu.getUniqueForEntranceName();
                if (name.isEmpty()){
                    continue;
                }
                currentUser = userService.signIn(name);
            } else if (userChoice == 2) {
                String name = userMenu.getUniqueForRegistrationName();
                currentUser = new User(name, false);
                userService.registration(currentUser);
                currentUser = userService.signIn(name);
            } else {
                break;
            }

            int currentUserBookShelfSize = currentUser.getReaderShelfSize();
            System.out.println(UserMenuImpl.BOOK_BALANCE + currentUserBookShelfSize);
            currentUser.showUserShelf();
            System.out.println(UserMenuImpl.CAN_TAKE + (2 - currentUserBookShelfSize) + UserMenuImpl.BOOKS);

            while (true) {

                userMenu.getMainLogicMenu();

                int userMainMenuChoice= userMenu.getRequiredMainMenuNumber();

                if (userMainMenuChoice == 1) {
                    bookService.printLibrary();
                    int requiredNumberShelf = userMenu.getShelfNumber();
                    int requiredNumberBook = userMenu.getBookNumber();
                    Book book = bookService.getBookFromLibrary(requiredNumberShelf, requiredNumberBook);
                    try {
                        userService.takeTheBook(book);
                    }catch (FullReaderShelfException exception){
                        System.out.println(exception.getMessage());
                        bookService.returnBookToLibrary(book);
                        logger.error(exception.getMessage());
                        continue;
                    }
                    long seconds = userMenu.getTime();
                    ReturnBookController returnBookController = new ReturnBookController(currentUser.getName(),book,userService,bookService,seconds);
                    new Thread(returnBookController).start();
                } else if (userMainMenuChoice == 2) {
                    System.out.println(UserMenuImpl.BOOKS_FROM_READING_HALL);
                    List<Book> available = userService.getBooksAvailableToExchange();
                    if (available.size() == 0){
                        System.out.println(UserMenuImpl.WHAT_ARE_DOING);
                        continue;
                    }
                    userService.printBooksAvailableToExchange(available);
                    int requiredNumberBookUser = userMenu.getNumberBookForExchange();
                    Book bookToExchange = currentUser.getReaderShelf().get(requiredNumberBookUser);
                    if (!bookToExchange.isAvailableToTakeHome()) {
                        List<Book> booksReadingHall = bookService.getReadingHall();
                        if (booksReadingHall.size() <= available.size()){
                            System.out.println(UserMenuImpl.NOTHING_TO_EXCHANGE);
                            continue;
                        }
                        bookService.printHall(booksReadingHall, available);
                        int requiredNumberBookHall = userMenu.getNumberBookHallForExchange();
                        Book bookFromAnotherUser = bookService.getBookFromReadingHall(booksReadingHall, requiredNumberBookHall);
                        User userForExchanging = userService.getUserForExchanging(bookFromAnotherUser);
                        new Thread(new ExchangeBookController(exchanger,bookToExchange,currentUser)).start();
                        new Thread( new ExchangeBookController(exchanger,bookFromAnotherUser,userForExchanging)).start();
                    }
                } else if (userMainMenuChoice == 3) {
                    System.out.println(UserMenuImpl.YOUR_BOOKS);
                    currentUser.showUserShelf();
                    int requiredNumberBookUser = userMenu.getBookNumber();
                    Book bookToReturn = currentUser.getReaderShelf().get(requiredNumberBookUser);
                    userService.returnTheBook(bookToReturn);
                    bookService.returnBookToLibrary(bookToReturn);
                    System.out.println(UserMenuImpl.YOUR_BOOKS);
                    currentUser.showUserShelf();
                } else{
                    userService.signOut();
                    break;
                }
            }
        }
        logger.info("The main thread is ended");
    }
}

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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Controller {

    public static void main(String[] args) throws IOException, InterruptedException {
        BookService bookService = new BookServiceImpl(new Library("Sofia's library"));
        User currentUser = null;

        bookService.createGoBooksList();
        bookService.createJavaBooksList();
        bookService.createJavaScriptBooksList();
        bookService.createRubyBooksList();
        bookService.createPythonBooksList();

        Semaphore semaphore = new Semaphore(1);
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
//                        logger.error(exception.getMessage());
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
                        bookService.printHall(booksReadingHall, available);
                        int requiredNumberBookHall = userMenu.getNumberBookHallForExchange();
                        Book bookFromAnotherUser = bookService.getBookFromReadingHall(booksReadingHall, requiredNumberBookHall);
                        User userForExchanging = userService.getUserForExchanging(bookFromAnotherUser);
                        userService.exchangeBooksWithAnotherUser(userForExchanging, bookToExchange, bookFromAnotherUser);
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
    }
}

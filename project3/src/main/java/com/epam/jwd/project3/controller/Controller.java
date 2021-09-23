package com.epam.jwd.project3.controller;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.composite.Library;
import com.epam.jwd.project3.model.user.User;
import com.epam.jwd.project3.repository.impl.UserRepositoryImpl;
import com.epam.jwd.project3.service.api.BookService;
import com.epam.jwd.project3.service.api.UserService;
import com.epam.jwd.project3.service.impl.BookServiceImpl;
import com.epam.jwd.project3.service.impl.UserServiceImpl;
import com.epam.jwd.project3.view.impl.UserMenuImpl;

import java.io.IOException;
import java.util.List;

public class Controller {

    public static void main(String[] args) throws IOException {
        BookService bookService = new BookServiceImpl(new Library("Sofia's library"));
        User currentUser = null;

        bookService.createGoBooksList();
        bookService.createJavaBooksList();
        bookService.createJavaScriptBooksList();
        bookService.createRubyBooksList();
        bookService.createPythonBooksList();

        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        UserMenuImpl userMenuImpl = new UserMenuImpl();

        while (true) {

            userMenuImpl.getStartMenu();
            int userChoice = userMenuImpl.getRequiredNumber();

            if (userChoice == 1) {
                String name = userMenuImpl.getUniqueName();
                currentUser = userService.signIn(name);
            } else if (userChoice == 2) {
                String name = userMenuImpl.getUniqueName();
                currentUser = new User(name, false);
                userService.registration(currentUser);
            } else {
                break;
            }

            int currentUserBookShelfSize = currentUser.getReaderShelfSize();
            System.out.println(UserMenuImpl.BOOK_BALANCE + currentUserBookShelfSize);
            currentUser.showUserShelf();
            System.out.println(UserMenuImpl.CAN_TAKE + (2 - currentUserBookShelfSize) + UserMenuImpl.BOOKS);

            while (true) {

                userMenuImpl.getMainLogicMenu();

                int userMainMenuChoice= userMenuImpl.getRequiredMainMenuNumber();

                if (userMainMenuChoice == 1) {
                    bookService.printLibrary();
                    int requiredNumberShelf = userMenuImpl.getShelfNumber();
                    int requiredNumberBook = userMenuImpl.getBookNumber();
                    Book book = bookService.getBookFromLibrary(requiredNumberShelf, requiredNumberBook);
                    userService.takeTheBook(book);
                } else if (userMainMenuChoice == 2) {
                    System.out.println(UserMenuImpl.BOOKS_FROM_READING_HALL);
                    List<Book> available = userService.getBooksAvailableToExchange();
                    userService.printBooksAvailableToExchange(available);
                    int requiredNumberBookUser = userMenuImpl.getNumberBookForExchange();
                    Book bookToExchange = currentUser.getReaderShelf().get(requiredNumberBookUser);
                    if (!bookToExchange.isAvailableToTakeHome()) {
                        List<Book> booksReadingHall = bookService.getReadingHall();
                        bookService.printHall(booksReadingHall, available);
                        int requiredNumberBookHall = userMenuImpl.getNumberBookHallForExchange();
                        Book bookFromAnotherUser = bookService.getBookFromReadingHall(booksReadingHall, requiredNumberBookHall);
                        User userForExchanging = userService.getUserForExchanging(bookFromAnotherUser);
                        userService.exchangeBooksWithAnotherUser(userForExchanging, bookToExchange, bookFromAnotherUser);
                    }
                } else if (userMainMenuChoice == 3) {
                    System.out.println(UserMenuImpl.YOUR_BOOKS);
                    currentUser.showUserShelf();
                    int requiredNumberBookUser = userMenuImpl.getBookNumber();
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

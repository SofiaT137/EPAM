package com.epam.jwd.project3.service.api;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.user.User;
import com.epam.jwd.project3.service.exception.FullReaderShelfException;

import java.util.List;

public interface UserService {

    void registration(User user);

    User signIn(String userName) throws InterruptedException;

    void exchangeBooksWithAnotherUser(User anotherUser,Book usersBook,Book requestBook);

    List<Book> getBooksAvailableToExchange();

    void printBooksAvailableToExchange(List<Book> booksAvailableToExchange);

    User getUserForExchanging(Book book);

    void takeTheBook(Book book) throws FullReaderShelfException;

    void returnTheBook(Book book);

    void signOut();



}

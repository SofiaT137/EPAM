package com.epam.jwd.project3.service.api;

import com.epam.jwd.project3.model.composite.Book;

import java.util.List;

public interface BookService {

    void createGoBooksList();

    void createJavaBooksList();

    void createJavaScriptBooksList();

    void createPythonBooksList();

    void createRubyBooksList();

    void printLibrary();

    List<Book> getReadingHall();

    void printHall(List<Book> getReadingHall,List<Book> getUsersBooks) ;

    Book getBookFromReadingHall(List<Book> getReadingHall,int requiredNumber);

    Book getBookFromLibrary(int shellIndex, int bookIndex);

    void returnBookToLibrary(Book book);
}
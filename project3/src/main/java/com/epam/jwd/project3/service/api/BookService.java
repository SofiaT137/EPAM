package com.epam.jwd.project3.service.api;

import com.epam.jwd.project3.model.composite.Book;

/**
 *
 */
public interface BookService {

    void createGoBooksList();
    void createJavaBooksList();
    void createJavaScriptBooksList();
    void createPythonBooksList();
    void createRubyBooksList();

    void printLibrary();
    void printHall();
    Book getBookFromLibrary(int shellIndex, int bookIndex);
}

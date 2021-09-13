package com.epam.jwd.project3.service.impl;

import com.epam.jwd.project3.model.Book;
import com.epam.jwd.project3.model.Composite;
import com.epam.jwd.project3.model.Library;
import com.epam.jwd.project3.model.Shell;
import com.epam.jwd.project3.service.api.BookFactory;
import com.epam.jwd.project3.service.api.BookService;
import com.epam.jwd.project3.service.book_factory.*;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {

    private Library library;

    public BookServiceImpl(Library library) {
        this.library = library;
    }

    @Override
    public void createGoBooksList() {
        BookFactory goFactory = new GoBookFactory();
        fillLibrary(getListOfBooks(goFactory,"Go"));
    }

    @Override
    public void createJavaBooksList() {
        BookFactory javaFactory = new JavaBookFactory();
        fillLibrary(getListOfBooks(javaFactory,"Java"));
    }

    public void createJavaScriptBooksList() {
        BookFactory javaScriptFactory = new JavaScriptBookFactory();
        fillLibrary(getListOfBooks(javaScriptFactory,"JavaScript"));
    }

    public void createPythonBooksList() {
        BookFactory pythonFactory = new PythonBookFactory();
        fillLibrary(getListOfBooks(pythonFactory,"Python"));
    }

    @Override
    public void createRubyBooksList() {
        BookFactory rubyFactory = new RubyBookFactory();
        fillLibrary(getListOfBooks(rubyFactory,"Ruby"));
    }

    @Override
    public void print() {
        System.out.println(library);
    }


    private void fillLibrary(Composite shell) {
        library.add(shell);
    }


    private Composite getListOfBooks(BookFactory bookFactory,String shellName) {
        Shell shell = new Shell(shellName);
        shell.add(bookFactory.createReadingRoomBook());
        shell.add(bookFactory.createTakingHomeBook());
        return shell;
    }



}

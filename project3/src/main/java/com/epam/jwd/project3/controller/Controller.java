package com.epam.jwd.project3.controller;

import com.epam.jwd.project3.model.Library;
import com.epam.jwd.project3.service.api.BookService;
import com.epam.jwd.project3.service.impl.BookServiceImpl;

public class Controller {
    public static void main(String[] args) {
        BookService service = new BookServiceImpl(new Library("Sofia's library"));
        service.createGoBooksList();
        service.createJavaBooksList();
        service.createJavaScriptBooksList();
        service.createRubyBooksList();
        service.createPythonBooksList();
        service.print();
    }
}

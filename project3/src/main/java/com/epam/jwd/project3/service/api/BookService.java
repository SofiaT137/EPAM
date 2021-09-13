package com.epam.jwd.project3.service.api;

public interface BookService {

    void createGoBooksList();
    void createJavaBooksList();
    void createJavaScriptBooksList();
    void createPythonBooksList();
    void createRubyBooksList();

    void print();
}

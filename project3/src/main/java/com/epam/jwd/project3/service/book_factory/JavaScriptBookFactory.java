package com.epam.jwd.project3.service.book_factory;

import com.epam.jwd.project3.model.Book;
import com.epam.jwd.project3.model.Composite;
import com.epam.jwd.project3.service.api.BookFactory;

public class JavaScriptBookFactory implements BookFactory {
    @Override
    public Composite createReadingRoomBook() {
        return new Book("JavaScript: The Definitive Guide","David Flanagan",2020,706,false);
    }

    @Override
    public Composite createTakingHomeBook() {
        return new Book("JavaScript: The Good Parts","Douglas Crockford",2008,176,true);
    }
}
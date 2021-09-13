package com.epam.jwd.project3.service.book_factory;

import com.epam.jwd.project3.model.Book;
import com.epam.jwd.project3.model.Composite;
import com.epam.jwd.project3.service.api.BookFactory;

public class RubyBookFactory implements BookFactory {


    @Override
    public Composite createReadingRoomBook() {
        return new Book("The Ruby Programming Language: Everything You Need to Know","David Flanagan",2008,448,false);
    }

    @Override
    public Composite createTakingHomeBook() {
        return new Book("The Well-Grounded Rubyist","David A. Black",2014,536,true);
    }
}

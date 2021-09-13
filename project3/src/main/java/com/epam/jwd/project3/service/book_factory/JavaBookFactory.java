package com.epam.jwd.project3.service.book_factory;

import com.epam.jwd.project3.model.Book;
import com.epam.jwd.project3.service.api.BookFactory;

public class JavaBookFactory implements BookFactory {
    @Override
    public Book createReadingRoomBook() {
        return new Book("Java: The Complete Reference, Tenth Edition (Complete Reference Series)","Herbert Schildt ",2017,500,false);
    }

    @Override
    public Book createTakingHomeBook() {
        return new Book("Thinking in Java 4th Edition","Bruce Eckel",2006,1067,true);
    }
}

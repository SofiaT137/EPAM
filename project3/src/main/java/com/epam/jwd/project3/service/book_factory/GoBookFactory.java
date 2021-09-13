package com.epam.jwd.project3.service.book_factory;

import com.epam.jwd.project3.model.Book;
import com.epam.jwd.project3.service.api.BookFactory;

public class GoBookFactory implements BookFactory {
    @Override
    public Book createReadingRoomBook() {
        return new Book("Hands-on Go Programming","Sachchidanand Singh",2021,249,false);
    }

    @Override
    public Book createTakingHomeBook() {
        return new Book("Learning Go: An Idiomatic Approach to Real-World Go Programming","Jon Bodner ",2021,374,true);
    }
}

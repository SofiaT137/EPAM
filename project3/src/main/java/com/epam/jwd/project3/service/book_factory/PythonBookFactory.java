package com.epam.jwd.project3.service.book_factory;

import com.epam.jwd.project3.model.Book;
import com.epam.jwd.project3.service.api.BookFactory;

public class PythonBookFactory implements BookFactory {
    @Override
    public Book createReadingRoomBook() {
        return new Book("Think Python: How to Think Like a Computer Scientist, 2nd edition","Allen B. Downey",2016,292,false);
    }

    @Override
    public Book createTakingHomeBook() {
        return new Book("Learn Python 3 the Hard Way","Zed A. Shaw",2017,320,true);
    }
}

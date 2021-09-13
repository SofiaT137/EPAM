package com.epam.jwd.project3.service.api;

import com.epam.jwd.project3.model.Book;

public interface BookFactory {

    Book createReadingRoomBook();

    Book createTakingHomeBook();
}

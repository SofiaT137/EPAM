package com.epam.jwd.project3.service.api;

import com.epam.jwd.project3.model.composite.Composite;

public interface BookFactory {

    Composite createReadingRoomBook();

    Composite createTakingHomeBook();
}

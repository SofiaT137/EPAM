package com.epam.jwd.project3.service.api;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.user.User;

public interface UserService {

    void registration(User user);
    User signIn(String userName);

    void exchangeBooksWithAnotherUser(User other,Book requestBook);

    void takeTheBook(Book book);

    void returnTheBook(Book book);

    void signOut();

}

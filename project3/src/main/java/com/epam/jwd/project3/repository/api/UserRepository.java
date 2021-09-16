package com.epam.jwd.project3.repository.api;


import com.epam.jwd.project3.model.user.User;

public interface UserRepository {

    void save(User user);

    User findByUserName(String userName);

    boolean delete(User user);
}

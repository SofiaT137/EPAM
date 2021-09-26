package com.epam.jwd.project3.repository.impl;

import com.epam.jwd.project3.controller.ReturnBookController;
import com.epam.jwd.project3.model.user.User;
import com.epam.jwd.project3.repository.api.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final List<User> userStorage = new ArrayList<>();


    @Override
    public void save(User user) {
        userStorage.add(user);
    }

    public List<User> getUserStorage() {
        return userStorage;
    }

    @Override
    public User findByUserName(String userName) {
        for (User user : userStorage) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        throw new NullPointerException("I can't find user named: " + userName);
    }
    @Override
    public boolean delete(User user) {
        return userStorage.remove(user);
    }
}

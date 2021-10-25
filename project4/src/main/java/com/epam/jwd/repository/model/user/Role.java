package com.epam.jwd.repository.model.user;

import java.util.Arrays;

public enum Role{
    ADMIN(1),
    STUDENT(2),
    TEACHER(3),
    HEADMAN(4);

    private int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Role getById(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
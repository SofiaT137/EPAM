package com.epam.jwd.DAO.model.user;


import java.util.Arrays;


public enum Role {
    ADMINISTRATOR(1),
    TEACHER(2),
    STUDENT(3);

    private final Integer id;

    Role(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Role getById(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getId() == id)
                .findFirst()
                .orElse(null);
    }
}

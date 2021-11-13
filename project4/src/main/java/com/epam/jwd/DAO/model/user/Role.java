package com.epam.jwd.DAO.model.user;

import java.util.Arrays;

public enum Role {
    ADMINISTRATOR("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Role getByName(String name) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}

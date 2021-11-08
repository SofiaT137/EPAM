package com.epam.jwd.controller.command;

import java.util.Arrays;
import com.epam.jwd.controller.command.api.Command;

public enum Commands {
    DEFAULT(DefaultCommand.getInstance()),
    GET_ALL_COURSE(GetAllCourseCommand.getInstance()),
    GET_ALL_USER(GetAllUserCommand.getInstance());

    private Command command;

    Commands(Command command) {
        this.command = command;
    }

    public static Command getCommand(String name) {
        return Arrays.stream(Commands.values())
                .filter(command -> command.name().equalsIgnoreCase(name))
                .map(command -> command.command)
                .findFirst()
                .orElse(DefaultCommand.getInstance());
    }
}

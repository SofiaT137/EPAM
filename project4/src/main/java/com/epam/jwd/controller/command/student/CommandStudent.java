package com.epam.jwd.controller.command.student;

import com.epam.jwd.controller.command.api.Command;

import java.util.Arrays;

public enum CommandStudent {
    DEFAULT(DefaultCommand.getInstance()),
    CREATE_STUDENT_ACCOUNT(CreateStudentAccount.getInstance());

    private Command command;

    CommandStudent(Command command){
        this.command = command;
    }

    public static Command getCommand(String name){
        return Arrays.stream(CommandStudent.values())
                .filter(command -> command.name().equalsIgnoreCase(name))
                .map(command -> command.command)
                .findFirst()
                .orElse(DefaultCommand.getInstance());
    }

}

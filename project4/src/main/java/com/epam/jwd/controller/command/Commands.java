package com.epam.jwd.controller.command;

import java.util.Arrays;

import com.epam.jwd.controller.command.ShowPageCommand.ShowPossibleCoursePageCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowUserPageCommand;
import com.epam.jwd.controller.command.api.Command;

public enum Commands {
    DEFAULT(DefaultCommand.getInstance()),
    GET_ALL_COURSE(GetAllCourseCommand.getInstance()),
    GET_ALL_USER(GetAllUserCommand.getInstance()),
    GET_ALL_REVIEW(GetAllReviewCommand.getInstance()),
    SELECT_REGISTRATION_OR_LOG_IN(SelectRegistrationOrLogInCommand.getInstance()),
    SHOW_USER_PAGE_COMMAND(ShowUserPageCommand.getInstance()),
    SHOW_POSSIBLE_PAGE_COMMAND(ShowPossibleCoursePageCommand.getInstance()),
    USER_PAGE_COMMAND(UserPageCommand.getInstance()),
    REGISTER_USER_COMMAND(RegisterUserCommand.getInstance()),
    GET_BACK_OR_LOG_OUT_COMMAND(GetBackOrLogOutCommand.getInstance()),
    DELETE_USER_COURSE_COMMAND(DeleteUserCourseCommand.getInstance()),
    SIGN_UP_TO_COURSE_COMMAND(SignUpToCourseCommand.getInstance()),
    LOG_OUT_COMMAND(LogOutCommand.getInstance());


    private final Command command;

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

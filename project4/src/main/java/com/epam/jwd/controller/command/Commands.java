package com.epam.jwd.controller.command;

import java.util.Arrays;

import com.epam.jwd.controller.command.ShowPageCommand.ShowCreateCoursePageCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowDeleteCoursePageCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowDeleteUserPageCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowErrorPageCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowPossibleCoursePageCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowRatePageCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowRegisterPageCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowReviewPageCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowTeacherCourseCommand;
import com.epam.jwd.controller.command.ShowPageCommand.ShowTeacherPageCommand;
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
    SHOW_DELETE_PAGE_COMMAND(ShowDeleteUserPageCommand.getInstance()),
    SHOW_REVIEW_PAGE_COMMAND(ShowReviewPageCommand.getInstance()),
    SHOW_ERROR_PAGE_COMMAND(ShowErrorPageCommand.getInstance()),
    SHOW_REGISTER_PAGE_COMMAND(ShowRegisterPageCommand.getInstance()),
    SHOW_RATE_PAGE_COMMAND(ShowRatePageCommand.getInstance()),
    SHOW_TEACHER_PAGE_COMMAND(ShowTeacherPageCommand.getInstance()),
    SHOW_CREATE_COURSE_PAGE_COMMAND(ShowCreateCoursePageCommand.getInstance()),
    SHOW_DELETE_COURSE_PAGE_COMMAND(ShowDeleteCoursePageCommand.getInstance()),
    SHOW_TEACHER_COURSE_COMMAND(ShowTeacherCourseCommand.getInstance()),
    TEACHER_SELECT_COURSE_COMMAND(TeacherSelectCourseCommand.getInstance()),
    TEACHER_PAGE_COMMAND(TeacherPageCommand.getInstance()),
    CREATE_COURSE_COMMAND(CreateCourseCommand.getInstance()),
    RATE_STUDENT_COMMAND(RateStudentCommand.getInstance()),
    USER_PAGE_COMMAND(UserPageCommand.getInstance()),
    REGISTER_USER_COMMAND(RegisterUserCommand.getInstance()),
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

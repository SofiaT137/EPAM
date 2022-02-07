package com.epam.jwd.controller.command;

import java.util.Arrays;

import com.epam.jwd.controller.command.show_page_command.*;

/**
 * The all commands enum
 */
public enum Commands {
    DEFAULT(DefaultCommand.getInstance()),
    SHOW_ALL_COURSE(ShowAllCourseCommand.getInstance()),
    SHOW_ALL_USER(ShowAllUserCommand.getInstance()),
    SHOW_ALL_REVIEW(ShowAllReviewCommand.getInstance()),
    SELECT_REGISTRATION_OR_LOG_IN(SelectRegistrationOrLogInCommand.getInstance()),
    SHOW_USER_PAGE_COMMAND(ShowUserPageCommand.getInstance()),
    SHOW_POSSIBLE_PAGE_COMMAND(ShowPossibleCoursePageCommand.getInstance()),
    SHOW_DELETE_PAGE_COMMAND(ShowDeleteUserPageCommand.getInstance()),
    SHOW_REVIEW_PAGE_COMMAND(ShowReviewPageCommand.getInstance()),
    SHOW_REGISTER_PAGE_COMMAND(ShowRegisterPageCommand.getInstance()),
    SHOW_RATE_PAGE_COMMAND(ShowRatePageCommand.getInstance()),
    SHOW_TEACHER_PAGE_COMMAND(ShowTeacherPageCommand.getInstance()),
    SHOW_CREATE_COURSE_PAGE_COMMAND(ShowCreateCoursePageCommand.getInstance()),
    SHOW_DELETE_COURSE_PAGE_COMMAND(ShowDeleteCoursePageCommand.getInstance()),
    SHOW_TEACHER_COURSE_COMMAND(ShowTeacherCourseCommand.getInstance()),
    SHOW_UPDATE_COURSE_COMMAND(ShowUpdateCourseCommand.getInstance()),
    SHOW_ADMIN_PAGE_COMMAND(ShowAdminPageCommand.getInstance()),
    SHOW_CREATE_TEACHER_PAGE_COMMAND(ShowCreateTeacherPageCommand.getInstance()),
    SHOW_BLOCK_USER_PAGE_COMMAND(ShowBlockUserPageCommand.getInstance()),
    SHOW_CREATE_GROUP_PAGE_COMMAND(ShowCreateGroupPageCommand.getInstance()),
    CREATE_GROUP_COMMAND(CreateGroupCommand.getInstance()),
    CHANGE_LANGUAGE_COMMAND(ChangeLanguageCommand.getInstance()),
    UPDATE_COURSE_COMMAND(UpdateCourseCommand.getInstance()),
    TEACHER_SELECT_COURSE_COMMAND(TeacherSelectCourseCommand.getInstance()),
    TEACHER_PAGE_COMMAND(TeacherPageCommand.getInstance()),
    CREATE_COURSE_COMMAND(CreateCourseCommand.getInstance()),
    RATE_STUDENT_COMMAND(RateStudentCommand.getInstance()),
    USER_PAGE_COMMAND(UserPageCommand.getInstance()),
    REGISTER_USER_COMMAND(RegisterUserCommand.getInstance()),
    DELETE_USER_COURSE_COMMAND(DeleteUserCourseCommand.getInstance()),
    DELETE_COURSE_COMMAND(DeleteCourseCommand.getInstance()),
    SIGN_UP_TO_COURSE_COMMAND(SignUpToCourseCommand.getInstance()),
    ADMIN_PAGE_COMMAND(AdminPageCommand.getInstance()),
    CREATE_TEACHER_COMMAND(CreateTeacherCommand.getInstance()),
    BLOCK_USER_COMMAND(BlockUserCommand.getInstance()),
    LOG_OUT_COMMAND(LogOutCommand.getInstance());

    private final Command command;

    Commands(Command command) {
        this.command = command;
    }

    /**
     * Get requested command or default command
     * @param name of the command
     * @return requested command
     */
    public static Command getCommand(String name) {
        return Arrays.stream(Commands.values())
                .filter(command -> command.name().equalsIgnoreCase(name))
                .map(command -> command.command)
                .findFirst()
                .orElse(DefaultCommand.getInstance());
    }
}

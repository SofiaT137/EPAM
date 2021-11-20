package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class CreateCourseCommand implements Command {

    private static final Command INSTANCE = new CreateCourseCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private CreateCourseCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}

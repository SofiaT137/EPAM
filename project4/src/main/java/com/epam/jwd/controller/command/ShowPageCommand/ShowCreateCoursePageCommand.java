package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class ShowCreateCoursePageCommand implements Command {

    private static final Command INSTANCE = new ShowCreateCoursePageCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowCreateCoursePageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}

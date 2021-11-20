package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class ShowDeleteCoursePageCommand implements Command {

    private static final Command INSTANCE = new ShowDeleteCoursePageCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowDeleteCoursePageCommand() {

    }
    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}

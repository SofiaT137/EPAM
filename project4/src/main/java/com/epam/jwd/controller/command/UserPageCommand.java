package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class UserPageCommand implements Command {

    private static final Command INSTANCE = new UserPageCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private UserPageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}

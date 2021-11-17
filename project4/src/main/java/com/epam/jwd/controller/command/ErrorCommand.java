package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class ErrorCommand implements Command {

    private static final Command INSTANCE = new ErrorCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ErrorCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}

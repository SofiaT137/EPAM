package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class RegisterUserCommand implements Command {

    private static final Command INSTANCE = new RegisterUserCommand();

    public static Command getInstance() {
        return INSTANCE;
    }



    private RegisterUserCommand() {

    }


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}

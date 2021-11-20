package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class ShowRatePageCommand implements Command {

    private static final Command INSTANCE = new ShowRatePageCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowRatePageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}

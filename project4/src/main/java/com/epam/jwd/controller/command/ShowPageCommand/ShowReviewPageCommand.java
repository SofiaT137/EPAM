package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class ShowReviewPageCommand implements Command {

    private static final Command INSTANCE = new ShowReviewPageCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowReviewPageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}

package com.epam.jwd.controller.command.student;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;

public class DefaultCommand implements Command {

    private static final Command INSTANCE = new DefaultCommand();

    public static Command getInstance(){
        return INSTANCE;
    }

    private static final ResponseContext DEFAULT_PAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/main.jsp";
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    DefaultCommand(){

    }

    @Override
    public ResponseContext execute(RequestContext context) {
        return null;
    }
}

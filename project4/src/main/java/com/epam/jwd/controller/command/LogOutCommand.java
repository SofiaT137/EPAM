package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.userdto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;

/**
 * Log out command
 */
public class LogOutCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LogOutCommand.class);

    private static final Command INSTANCE = new LogOutCommand();
    private static final String MAIN_JSP = "/index.jsp";

    public static Command getInstance() {
        return INSTANCE;
    }

    private LogOutCommand() {

    }

    private static final ResponseContext MAIN_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return MAIN_JSP;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");

        if (userDto != null) {
            String msg = MessageFormat.format("{0},{1} is logged out.",userDto.getFirstName(),userDto.getLastName());
            LOGGER.info(msg);
        }
        requestContext.invalidateCurrentSession();
        return MAIN_PAGE_CONTEXT;
    }
}

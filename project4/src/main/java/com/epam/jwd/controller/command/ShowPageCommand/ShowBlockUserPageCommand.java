package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

public class ShowBlockUserPageCommand implements Command {

    private static final Command INSTANCE = new ShowBlockUserPageCommand();

    private static final String BLOCK_USER_JSP = "/WEB-INF/jsp/block_user.jsp";
    private static final String BLOCK_USERS_JSP_COLLECTION_ATTRIBUTE = "blocked_users";

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowBlockUserPageCommand() {

    }

    private static final ResponseContext BLOCK_USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return BLOCK_USER_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<UserDto> blockedUsers = (List<UserDto>) requestContext.getAttributeFromSession("blockedUsers");
        requestContext.addAttributeToJSP(BLOCK_USERS_JSP_COLLECTION_ATTRIBUTE, blockedUsers);
        return BLOCK_USER_PAGE_CONTEXT ;
    }
}

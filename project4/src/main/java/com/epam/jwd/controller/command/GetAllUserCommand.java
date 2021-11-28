package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.userdto.UserDto;


import java.util.List;

public class GetAllUserCommand implements Command {

    private static final Command INSTANCE = new GetAllUserCommand();

    private static final String USERS_JSP = "/WEB-INF/jsp/all_users.jsp";

    private static final String USERS_JSP_USERS_COLLECTION_ATTRIBUTE = "all_users";

    public static Command getInstance(){
        return INSTANCE;
    }

    private GetAllUserCommand(){

    }

    private static final ResponseContext GET_USERS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return USERS_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<UserDto> all_users = (List<UserDto>) requestContext.getAttributeFromSession("allUsers");
        requestContext.addAttributeToJSP(USERS_JSP_USERS_COLLECTION_ATTRIBUTE, all_users);
        return GET_USERS_CONTEXT;
    }
}

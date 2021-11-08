package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.UserService;

import java.util.List;

public class GetAllUserCommand implements Command {

    private static final Command INSTANCE = new GetAllUserCommand();
    private final Service<UserDto,Integer> service = new UserService();
    private static final String USERS_JSP = "/WEB-INF/jsp/users.jsp";
    private static final String USERS_JSP_USERS_COLLECTION_ATTRIBUTE = "users";

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

    public static Command getInstance(){
        return INSTANCE;
    }

    private GetAllUserCommand(){

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<UserDto> userDtoList = service.getAll();
        requestContext.addAttributeToJSP(USERS_JSP_USERS_COLLECTION_ATTRIBUTE,userDtoList);
        return GET_USERS_CONTEXT;
    }
}

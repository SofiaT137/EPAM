package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.impl.AccountService;

public class RegisterAccountCommand implements Command {

    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
      private static final String REGISTRATION_USER_JSP = "/registration_user.jsp";
    private static final Command INSTANCE = new RegisterAccountCommand();
    private final Service<AccountDto,Integer> service = new AccountService();
    private static final String REGISTER_ACCOUNT_JSP_COLLECTION_ATTRIBUTE = "register_account";

    private static final ResponseContext GET_ACCOUNT_REGISTRATION_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REGISTRATION_USER_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    private RegisterAccountCommand(){

    }


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String login = requestContext.getParameterFromJSP(USER_LOGIN);
        String password = requestContext.getParameterFromJSP(USER_PASSWORD);
//        requestContext.addAttributeToJSP(REGISTER_ACCOUNT_JSP_COLLECTION_ATTRIBUTE,account);
        return GET_ACCOUNT_REGISTRATION_CONTEXT;
    }
}

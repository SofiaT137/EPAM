package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.impl.AccountService;

import java.util.List;
import java.util.NoSuchElementException;


public class SelectRegistrationOrLogInCommand implements Command {

    private static final Command INSTANCE = new SelectRegistrationOrLogInCommand();
    private static final String REGISTER_USER_JSP = "/WEB-INF/jsp/register_user.jsp";
    private static final String USER_PAGE_JSP = "/WEB-INF/jsp/user_page.jsp";
    private final Service<AccountDto, Integer> service = new AccountService();
    private static final String REGISTER_USER_JSP_COLLECTION_ATTRIBUTE = "register_user";
    private static final String USER_PAGE_JSP_COLLECTION_ATTRIBUTE = "get_user_page";

    private static final ResponseContext REGISTER_USER_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REGISTER_USER_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    private static final ResponseContext USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return USER_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private SelectRegistrationOrLogInCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String login = requestContext.getParameterFromJSP("lblLogin");
        String password = requestContext.getParameterFromJSP("lblPassword");
        String role = requestContext.getParameterFromJSP("Role:");
        String btnRegister = requestContext.getParameterFromJSP("btnRegister");
        String btnLogIn = requestContext.getParameterFromJSP("btnLogIn");

        List<AccountDto> accountDtoList = ((AccountService) service).filterAccount(login);

        AccountDto accountDto = new AccountDto();
        accountDto.setRole(role);
        accountDto.setLogin(login);
        accountDto.setPassword(password);

        if (btnRegister != null) {

            if (accountDtoList.size() != 0) {
                return DefaultCommand.getInstance().execute(requestContext);
            }
            service.create(accountDto);

            requestContext.addAttributeToSession(REGISTER_USER_JSP_COLLECTION_ATTRIBUTE, accountDto);

            return REGISTER_USER_CONTEXT;
        } else if (btnLogIn != null) {
            if (accountDtoList.size() != 0) {
                accountDto = accountDtoList.get(0);
                requestContext.addAttributeToSession(USER_PAGE_JSP_COLLECTION_ATTRIBUTE, accountDto);
            } else {
                return DefaultCommand.getInstance().execute(requestContext);
            }
            return USER_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}


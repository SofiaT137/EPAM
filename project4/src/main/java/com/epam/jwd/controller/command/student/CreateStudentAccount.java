package com.epam.jwd.controller.command.student;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.controller.command.impl.RequestContextImpl;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.UserService;

public class CreateStudentAccount implements Command {

    private static final Command INSTANCE = new CreateStudentAccount();
    private final Service<AccountDto,Integer> service = new AccountService();
    private static final String CREATE_USER_JSP = "WEB-INF/jsp/create_user.jsp";
    private static final ResponseContext CREATE_STUDENT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_USER_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance(){
        return INSTANCE;
    }


    private CreateStudentAccount(){

    }

    @Override
    public ResponseContext execute(RequestContext context) {
//        service.create(context);
        return null;
    }
}

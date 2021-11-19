package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.model.user.User;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.UserService;


public class RegisterUserCommand implements Command {

    private static final Command INSTANCE = new RegisterUserCommand();
    private final Service<UserDto, Integer> serviceUser = new UserService();
    private static final String REGISTER_USER_SESSION_COLLECTION_ATTRIBUTE = "registerUser";
    private static final String USER_PAGE_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";

    private static final ResponseContext USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return USER_PAGE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private RegisterUserCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String firstName = requestContext.getParameterFromJSP("lblFirstName");
        String lastName = requestContext.getParameterFromJSP("lblLastName");
        String group = requestContext.getParameterFromJSP("lblGroup");
        AccountDto registerAccount = (AccountDto) requestContext.getAttributeFromSession("registerAccount");
        String btnRegister = requestContext.getParameterFromJSP("btnRegister");

        UserDto userDto = new UserDto();
        userDto.setAccount_id(registerAccount.getId());
        userDto.setGroup_id(Integer.parseInt(group));
        userDto.setFirst_name(firstName);
        userDto.setLast_name(lastName);

        if (btnRegister != null) {
            UserDto createdUser = serviceUser.create(userDto);
            requestContext.addAttributeToSession(REGISTER_USER_SESSION_COLLECTION_ATTRIBUTE, createdUser);
            return USER_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}

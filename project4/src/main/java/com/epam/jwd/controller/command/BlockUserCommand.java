package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.UserService;

import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

public class BlockUserCommand implements Command {

    private static final Command INSTANCE = new BlockUserCommand();
    private static final String ADMIN_PAGE_JSP = "/controller?command=SHOW_ADMIN_PAGE_COMMAND";
    private static final String BLOCK_PAGE_JSP = "/controller?command=SHOW_BLOCK_USER_PAGE_COMMAND";
    private static final String BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE = "blockedUsers";
    private final Service<UserDto, Integer> serviceUser = new UserService();
    private final Service<CourseDto, Integer> serviceCourse = new CourseService();
    private final Service<AccountDto, Integer> serviceAccount = new AccountService();


    public static Command getInstance() {
        return INSTANCE;
    }

    private BlockUserCommand() {

    }

    private static final ResponseContext ADMIN_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ADMIN_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext BLOCK_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return BLOCK_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String btnUnBlockUser = requestContext.getParameterFromJSP("btnUnBlockUser");
        String btnBlockUser = requestContext.getParameterFromJSP("btnBlockUser");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");

        String firstName = new String(((requestContext.getParameterFromJSP("lblFirstName")).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);
        String lastName = new String(((requestContext.getParameterFromJSP("lblLastName")).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);
        String group = requestContext.getParameterFromJSP("lblGroup");

        List<UserDto> allUser= serviceUser.getAll();
        List<UserDto> userDto = ((UserService)serviceUser).filterUser(firstName,lastName);
        List<UserDto> blockedUsers = (List<UserDto>) requestContext.getAttributeFromSession("blockedUsers");

        if (btnBlockUser != null){

            if (userDto.size() == 0 || userDto.get(0).getGroup_id() != 2) {
                //error page context
            }
            UserDto current_user = userDto.get(0);
            AccountDto currentAccount = serviceAccount.getById(current_user.getAccount_id());
            currentAccount.setIsActive(0);
            try {
                serviceAccount.update(currentAccount);
            } catch (ServerException exception) {
                //log
                //error page
            }
            List<UserDto> blockedUser = findBlockedUser(allUser);
            requestContext.addAttributeToSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE,blockedUser);
            return BLOCK_PAGE_CONTEXT;
        }else if (btnUnBlockUser != null){
            UserDto current_user = userDto.get(0);
            AccountDto currentAccount = serviceAccount.getById(current_user.getAccount_id());
            currentAccount.setIsActive(1);
            blockedUsers.removeAll(userDto);
            requestContext.addAttributeToSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE,blockedUsers);
            return BLOCK_PAGE_CONTEXT;
        }else if(btnGetBack != null){
            return ADMIN_RESULT_CONTEXT;
        }

        return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<UserDto> findBlockedUser(List<UserDto> list){
        List<UserDto> blockedUser = new ArrayList<>();
        for (UserDto userDto : list) {
            if (serviceAccount.getById(userDto.getAccount_id()).getIsActive() == 0){
                blockedUser.add(userDto);
            }
        }
        return blockedUser;
    }
}

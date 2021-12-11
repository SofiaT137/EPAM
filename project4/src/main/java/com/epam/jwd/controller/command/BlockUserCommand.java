package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.GroupService;
import com.epam.jwd.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BlockUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(BlockUserCommand.class);

    private static final Command INSTANCE = new BlockUserCommand();

    private static final String BLOCK_PAGE_JSP = "/controller?command=SHOW_BLOCK_USER_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";

    private static final String BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE = "blockedUsers";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";

    private static final String CANNOT_FIND_THIS_USER_IN_GROUP = "I cannot find in this group this person";
    private static final String CHECK_ACCOUNT_FOR_BLOCK = "This account is blocked!";
    private static final String CHECK_ACCOUNT_FOR_UNBLOCK = "This account is unblocked!";

    private final Service<UserDto, Integer> serviceUser = new UserService();
    private final Service<AccountDto, Integer> serviceAccount = new AccountService();
    private final Service<GroupDto, Integer> groupService = new GroupService();


    public static Command getInstance() {
        return INSTANCE;
    }

    private BlockUserCommand() {

    }

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

    private static final ResponseContext ERROR_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ERROR_COURSE_COMMAND;
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

        String firstName = requestContext.getParameterFromJSP("lblFirstName");
        String lastName = requestContext.getParameterFromJSP("lblLastName");
        String groupName = requestContext.getParameterFromJSP("Group");

        List<UserDto> allUser= serviceUser.getAll();
        List<UserDto> blockedUsers = (List<UserDto>) requestContext.getAttributeFromSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE);

        List<UserDto> userDto;

        try{
            userDto = ((UserService)serviceUser).filterUser(firstName,lastName);
        }catch (ServiceException serviceException){
            LOGGER.error(serviceException.getMessage());
            requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, serviceException.getMessage());
            return ERROR_PAGE_CONTEXT;
        }

        try{
           ((GroupService) groupService).filterGroup(groupName);
        }catch (DAOException exception){
           LOGGER.error(CANNOT_FIND_THIS_USER_IN_GROUP);
            requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
            return ERROR_PAGE_CONTEXT;
        }

        UserDto currentUser= userDto.get(0);

         if (!(currentUser.getGroupName().equals(groupName))){
             LOGGER.error(CANNOT_FIND_THIS_USER_IN_GROUP);
             requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, CANNOT_FIND_THIS_USER_IN_GROUP);
             return ERROR_PAGE_CONTEXT;
        }
        AccountDto currentAccount = serviceAccount.getById(currentUser.getAccountId());

         if (btnBlockUser != null){
            if (currentAccount.getIsActive() == 0){
                LOGGER.error(CHECK_ACCOUNT_FOR_BLOCK);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, CHECK_ACCOUNT_FOR_BLOCK);
                return ERROR_PAGE_CONTEXT;
            }

            currentAccount.setIsActive(0);

            try {
                serviceAccount.update(currentAccount);
            } catch (ServiceException exception) {
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }

            List<UserDto> blockedUser = findBlockedUser(allUser);
            requestContext.addAttributeToSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE,blockedUser);
            return BLOCK_PAGE_CONTEXT;

        }else if (btnUnBlockUser != null){
            if (currentAccount.getIsActive() == 1){
                LOGGER.error(CHECK_ACCOUNT_FOR_UNBLOCK);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, CHECK_ACCOUNT_FOR_UNBLOCK);
                return ERROR_PAGE_CONTEXT;
            }

            currentAccount.setIsActive(1);

            try {
                serviceAccount.update(currentAccount);
            } catch (ServiceException exception) {
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }
            blockedUsers.removeAll(userDto);
            requestContext.addAttributeToSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE,blockedUsers);
            return BLOCK_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<UserDto> findBlockedUser(List<UserDto> list){
        List<UserDto> blockedUser = new ArrayList<>();
        for (UserDto userDto
                : list) {
            if (serviceAccount.getById(userDto.getAccountId()).getIsActive() == 0){
                blockedUser.add(userDto);
            }
        }
        return blockedUser;
    }
}

package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.GroupServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The command of blocking user
 */
public class BlockUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(BlockUserCommand.class);

    private static final Command INSTANCE = new BlockUserCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String BLOCK_PAGE_JSP = "/controller?command=SHOW_BLOCK_USER_PAGE_COMMAND";

    private static final String BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE = "blockedUsers";

    private static final String CANNOT_FIND_THIS_USER_IN_GROUP = "cannotFindThisUserInGroup";
    private static final String CANNOT_FIND_ANY_USER_BY_NAME_AND_SURNAME = "cannotFindUserByFirstAndLastName";
    private static final String CHECK_ACCOUNT_FOR_BLOCK = "accountBlocked";
    private static final String CHECK_ACCOUNT_FOR_UNBLOCK = "accountUnblocked";
    private static final String BLOCK_USER_BUTTON = "btnBlockUser";
    private static final String FIRST_NAME_LABEL = "lblFirstName";
    private static final String LAST_NAME_LABEL = "lblLastName";
    private static final String GROUP_LABEL = "lblGroup";

    private final Service<UserDto, Integer> serviceUser = new UserServiceImpl();
    private final Service<AccountDto, Integer> serviceAccount = new AccountServiceImpl();
    private final Service<GroupDto, Integer> groupService = new GroupServiceImpl();


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

    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String btnBlockUser = requestContext.getParameterFromJSP(BLOCK_USER_BUTTON);
        String firstName = requestContext.getParameterFromJSP(FIRST_NAME_LABEL);
        String lastName = requestContext.getParameterFromJSP(LAST_NAME_LABEL);
        String groupName = requestContext.getParameterFromJSP(GROUP_LABEL);

        List<UserDto> allUser = serviceUser.findAll();
        List<UserDto> blockedUsers = (List<UserDto>) requestContext.getAttributeFromSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE);

        List<UserDto> soughtUsers = findAllUsersByFirstNameAndLastName(firstName,lastName);
        if (soughtUsers.isEmpty()){
            LOGGER.error(CANNOT_FIND_ANY_USER_BY_NAME_AND_SURNAME);
            ERROR_HANDLER.setError(CANNOT_FIND_ANY_USER_BY_NAME_AND_SURNAME,requestContext);
            return BLOCK_PAGE_CONTEXT;
        }

        GroupDto soughtGroup = findGroup(groupName);
        UserDto neededUser = findSoughtUser(soughtUsers,soughtGroup);
        if (neededUser.getFirstName() == null){
             LOGGER.error(CANNOT_FIND_THIS_USER_IN_GROUP);
             ERROR_HANDLER.setError(CANNOT_FIND_THIS_USER_IN_GROUP,requestContext);
            return BLOCK_PAGE_CONTEXT;
        }

        AccountDto currentAccount = serviceAccount.getById(neededUser.getAccountId());
        boolean needToBlock = (btnBlockUser != null);
        boolean isAccountBlocked = checkIfThisAccountBlocked(currentAccount);

        if (needToBlock && isAccountBlocked){
            LOGGER.info(CHECK_ACCOUNT_FOR_BLOCK);
            ERROR_HANDLER.setError(CHECK_ACCOUNT_FOR_BLOCK, requestContext);
            return BLOCK_PAGE_CONTEXT;
        }
        if(!needToBlock && !isAccountBlocked){
            LOGGER.info(CHECK_ACCOUNT_FOR_UNBLOCK);
            ERROR_HANDLER.setError(CHECK_ACCOUNT_FOR_UNBLOCK, requestContext);
            return BLOCK_PAGE_CONTEXT;
        }
        try{
            updateAccount(needToBlock ? 0 : 1, currentAccount);
            if (needToBlock){
                blockedUsers = findBlockedUser(allUser);
            } else {
                blockedUsers.remove(neededUser);
            }
            requestContext.addAttributeToSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE,blockedUsers);
        }catch (ServiceException exception){
            LOGGER.error(exception.getMessage());
            ERROR_HANDLER.setError(exception.getMessage(),requestContext);
        }
        return BLOCK_PAGE_CONTEXT;
    }

    private UserDto findSoughtUser(List<UserDto> allSoughtUsers, GroupDto soughtGroup){
        UserDto soughtUser = new UserDto();
        for (UserDto foundedUser : allSoughtUsers) {
            if (foundedUser.getGroupName().equals(soughtGroup.getName())){
                soughtUser = foundedUser;
            }
        }
        return soughtUser;
    }

    private List<UserDto> findAllUsersByFirstNameAndLastName(String firstName, String lastName){
        return ((UserServiceImpl)serviceUser).filterUser(firstName,lastName);
    }

    private GroupDto findGroup(String groupName){
        return ((GroupServiceImpl) groupService).filterGroup(groupName);
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

    private Boolean checkIfThisAccountBlocked(AccountDto currentAccount){
        return currentAccount.getIsActive() == 0;
    }

    private void updateAccount(Integer block, AccountDto currentAccount){
        currentAccount.setIsActive(block);
        serviceAccount.update(currentAccount);
    }

}

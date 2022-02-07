package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.exception.CommandException;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.impl.GroupServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The command of creating groups
 */
public class CreateGroupCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(CreateGroupCommand.class);

    private static final Command INSTANCE = new CreateGroupCommand();
    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_CREATE_GROUP_PAGE_COMMAND";
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private final Service<GroupDto, Integer> groupService = new GroupServiceImpl();

    private static final String ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE = "universityGroups";

    private static final String NOT_UNIQUE_GROUP_NAME = "notUnique";
    private static final String ADD_GROUP_BUTTON = "btnAddGroup";
    private static final String GROUP_NAME_LABEL = "lblGroupName";


    public static Command getInstance() {
        return INSTANCE;
    }

    private CreateGroupCommand() {

    }

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REFRESH_PAGE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnAddGroup = requestContext.getParameterFromJSP(ADD_GROUP_BUTTON);
        String groupName = requestContext.getParameterFromJSP(GROUP_NAME_LABEL);

        if (btnAddGroup !=null) {
            try{
                if (isGroupExists(groupName)){
                    throw new CommandException(NOT_UNIQUE_GROUP_NAME);
                }
                createGroup(groupName);
                List<GroupDto> allGroup = groupService.findAll();
                requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,allGroup);
            }catch (Exception exception){
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
            }
        }
        return REFRESH_PAGE_CONTEXT;
    }

    private void createGroup(String groupName){
        GroupDto newGroup = new GroupDto();
        newGroup.setName(groupName);
        groupService.create(newGroup);
    }

    private boolean isGroupExists(String groupName) {
        boolean isGroup= true;
        try {
            ((GroupServiceImpl) groupService).filterGroup(groupName);
        }catch (DAOException exception){
            isGroup= false;
        }
        return isGroup;
    }
}

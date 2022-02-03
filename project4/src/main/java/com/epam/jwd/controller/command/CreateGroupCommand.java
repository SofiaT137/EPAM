package com.epam.jwd.controller.command;

import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.impl.GroupServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final String UNIQUE_GROUP_NAME = "unique group name";


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
        String btnAddGroup = requestContext.getParameterFromJSP("btnAddGroup");

        String groupName = requestContext.getParameterFromJSP("lblGroupName");

        if (btnAddGroup !=null) {
            if(Boolean.FALSE.equals(ifThisGroupExists(groupName,requestContext))){
                createGroup(groupName);
            }
            List<GroupDto> allGroup = groupService.findAll();
            requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,allGroup);
            return REFRESH_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    private void createGroup(String groupName){
        GroupDto newGroup = new GroupDto();
        newGroup.setName(groupName);
        groupService.create(newGroup);
    }

    private Boolean ifThisGroupExists(String groupName,RequestContext requestContext){
        try{
            ((GroupServiceImpl) groupService).filterGroup(groupName);
            LOGGER.error(NOT_UNIQUE_GROUP_NAME);
            ERROR_HANDLER.setError(NOT_UNIQUE_GROUP_NAME,requestContext);
            return true;
        }catch (DAOException daoException){
            LOGGER.info(UNIQUE_GROUP_NAME);
            return false;
        }
    }
}

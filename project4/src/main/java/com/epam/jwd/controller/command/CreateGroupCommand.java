package com.epam.jwd.controller.command;

import com.epam.jwd.Dao.exception.DAOException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.impl.GroupService;
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
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";

    private final Service<GroupDto, Integer> groupService = new GroupService();

    private static final String ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE = "universityGroups";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";

    private static final String NOT_UNIQUE_GROUP_NAME = "This group name is not unique!";
    private static final String UNIQUE_GROUP_NAME = "This group name is unique!";


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
        String btnAddGroup = requestContext.getParameterFromJSP("btnAddGroup");

        String groupName = requestContext.getParameterFromJSP("lblGroupName");

        if (btnAddGroup !=null) {

            GroupDto groupDto = new GroupDto();

            try{
                groupDto = ((GroupService) groupService).filterGroup(groupName);
                LOGGER.error(NOT_UNIQUE_GROUP_NAME);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, NOT_UNIQUE_GROUP_NAME);
                return ERROR_PAGE_CONTEXT;
            }catch (DAOException daoException){
                LOGGER.info(UNIQUE_GROUP_NAME);
            }

            groupDto.setName(groupName);

            groupService.create(groupDto);

            List<GroupDto> allGroup = groupService.findAll();

            requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,allGroup);

            return REFRESH_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}

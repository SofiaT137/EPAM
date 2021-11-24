package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.GroupService;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

public class CreateGroupCommand implements Command {

    private static final Command INSTANCE = new CreateGroupCommand();
    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_CREATE_GROUP_PAGE_COMMAND";
    private static final String ADMIN_PAGE_COMMAND = "/controller?command=SHOW_ADMIN_PAGE_COMMAND";
    private final Service<GroupDto, Integer> groupService = new GroupService();
    private static final String ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE = "universityGroups";


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

    private static final ResponseContext ADMIN_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ADMIN_PAGE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnAddGroup = requestContext.getParameterFromJSP("btnAddGroup");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");

        String groupName = new String(((requestContext.getParameterFromJSP("lblGroupName")).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);

        if (btnAddGroup !=null) {

            GroupDto groupDto = new GroupDto();

            try{
                groupDto = ((GroupService) groupService).filterGroup(groupName);
                //log
                //error page
            }catch (DAOException daoException){

            }

            groupDto.setName(groupName);

            groupService.create(groupDto);
            List<GroupDto> allGroup = groupService.getAll();
            requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,allGroup);

            return REFRESH_PAGE_CONTEXT;
        }
        else if (btnGetBack != null){
            return ADMIN_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}

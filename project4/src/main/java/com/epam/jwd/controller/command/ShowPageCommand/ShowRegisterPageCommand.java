package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

public class ShowRegisterPageCommand implements Command {

    private static final Command INSTANCE = new ShowRegisterPageCommand();

    private static final String REGISTER_PAGE_JSP = "/WEB-INF/jsp/register_user.jsp";

    private static final String ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE = "all_groups";

    private static final ResponseContext REGISTER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REGISTER_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    public static Command getInstance() {
        return INSTANCE;
    }


    private ShowRegisterPageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<GroupDto> groupDtoList = (List<GroupDto>) requestContext.getAttributeFromSession("allGroups");
        requestContext.addAttributeToJSP(ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE, groupDtoList);
        return REGISTER_PAGE_CONTEXT;
    }
}

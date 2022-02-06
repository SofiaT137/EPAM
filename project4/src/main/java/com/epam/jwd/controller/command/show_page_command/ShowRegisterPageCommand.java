package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 * The command shows "user(student) register " page
 */

public class ShowRegisterPageCommand implements Command {

    private static final Command INSTANCE = new ShowRegisterPageCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String REGISTER_PAGE_JSP = "/WEB-INF/jsp/register_user.jsp";

    private static final String ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE = "all_groups";
    private static final String ALL_GROUPS = "allGroups";


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

        List<GroupDto> groupDtoList = (List<GroupDto>) requestContext.getAttributeFromSession(ALL_GROUPS);
        ERROR_HANDLER.flushError(requestContext);
        requestContext.addAttributeToJSP(ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE, groupDtoList);

        return REGISTER_PAGE_CONTEXT;
    }
}

package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

/**
 * The command shows "block user (by administrator)" page
 */

public class ShowBlockUserPageCommand implements Command {

    private static final Command INSTANCE = new ShowBlockUserPageCommand();

    private static final String BLOCK_USER_JSP = "/WEB-INF/jsp/block_user.jsp";

    private static final String BLOCK_USERS_JSP_COLLECTION_ATTRIBUTE = "blocked_users";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";
    private static final String ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE = "university_groups";

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowBlockUserPageCommand() {

    }

    private static final ResponseContext BLOCK_USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return BLOCK_USER_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<GroupDto> allUniversityGroup = (List<GroupDto>) requestContext.getAttributeFromSession("universityGroups");
        List<UserDto> blockedUsers = (List<UserDto>) requestContext.getAttributeFromSession("blockedUsers");
        final int recordsPerPage = 5;
        int numberOfRecords = blockedUsers.size();
        int numberOfPages  = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
        int page = 1;
        if(requestContext.getParameterFromJSP("page") != null){
            page = Integer.parseInt(requestContext.getParameterFromJSP("page"));
        }
        if (page < 1 || page > numberOfPages){
            page = 1;
        }
        List<UserDto> blockedUsersOnPage = blockedUsers.subList((page-1)*recordsPerPage,Math.min(page*recordsPerPage,numberOfRecords));
        requestContext.addAttributeToJSP(BLOCK_USERS_JSP_COLLECTION_ATTRIBUTE, blockedUsersOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, numberOfPages);
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);
        requestContext.addAttributeToJSP(ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE, allUniversityGroup);
        return BLOCK_USER_PAGE_CONTEXT ;
    }
}

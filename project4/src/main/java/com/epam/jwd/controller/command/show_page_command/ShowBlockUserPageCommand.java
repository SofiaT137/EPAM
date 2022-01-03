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
        requestContext.addAttributeToJSP(BLOCK_USERS_JSP_COLLECTION_ATTRIBUTE, blockedUsers);
        requestContext.addAttributeToJSP(ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE, allUniversityGroup);
        return BLOCK_USER_PAGE_CONTEXT ;
    }
}

package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.groupdto.GroupDto;

import java.util.List;

/**
 * The command shows "create group (by administrator)" page
 */
public class ShowCreateGroupPageCommand implements Command {

    private static final Command INSTANCE = new ShowCreateGroupPageCommand();
    private static final String CREATE_GROUP_PAGE_JSP = "/WEB-INF/jsp/create_group.jsp";
    private static final String UNIVERSITY_GROUPS_JSP_COLLECTION_ATTRIBUTE = "university_groups";

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowCreateGroupPageCommand() {

    }

    private static final ResponseContext CREATE_GROUP_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_GROUP_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<GroupDto> universityGroups = (List<GroupDto>) requestContext.getAttributeFromSession("universityGroups");
        requestContext.addAttributeToJSP(UNIVERSITY_GROUPS_JSP_COLLECTION_ATTRIBUTE, universityGroups);
        return CREATE_GROUP_PAGE_CONTEXT;
    }
}

package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.userdto.UserDto;


public class ShowAdminPageCommand implements Command {

    private static final Command INSTANCE = new ShowAdminPageCommand();
    private static final String ADMIN_PAGE_JSP = "/WEB-INF/jsp/admin_page.jsp";

    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowAdminPageCommand() {

    }

    private static final ResponseContext ADMIN_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ADMIN_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
        return ADMIN_PAGE_CONTEXT;
    }
}

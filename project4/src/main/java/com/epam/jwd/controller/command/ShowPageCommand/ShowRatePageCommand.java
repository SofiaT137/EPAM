package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

public class ShowRatePageCommand implements Command {

    private static final Command INSTANCE = new ShowRatePageCommand();

    private static final String RATE_STUDENT_JSP = "/WEB-INF/jsp/rate_student.jsp";

    private static final String COURSE_STUDENT_JSP_COLLECTION_ATTRIBUTE = "students_course";
    private static final String ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE = "all_groups";

    private static final ResponseContext RATE_STUDENT_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return RATE_STUDENT_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowRatePageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<UserDto> userDtoList = (List<UserDto>) requestContext.getAttributeFromSession("studentsCourse");
        List<GroupDto> groupDtoList = (List<GroupDto>) requestContext.getAttributeFromSession("allGroups");
        requestContext.addAttributeToJSP(ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE, groupDtoList);
        requestContext.addAttributeToJSP(COURSE_STUDENT_JSP_COLLECTION_ATTRIBUTE, userDtoList);
        return RATE_STUDENT_PAGE_CONTEXT;
    }
}

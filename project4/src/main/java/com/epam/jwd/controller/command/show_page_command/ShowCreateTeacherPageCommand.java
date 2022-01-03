package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

/**
 * The command shows "create teacher (by administrator)" page
 */
public class ShowCreateTeacherPageCommand implements Command {

    private static final Command INSTANCE = new ShowCreateTeacherPageCommand();
    private static final String CREATE_TEACHER_PAGE_JSP = "/WEB-INF/jsp/create_teacher.jsp";
    private static final String ALL_UNIVERSITY_TEACHERS_JSP_COLLECTION_ATTRIBUTE = "all_teachers";


    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowCreateTeacherPageCommand() {

    }

    private static final ResponseContext CREATE_TEACHER_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_TEACHER_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<UserDto> allTeachers = (List<UserDto>) requestContext.getAttributeFromSession("allTeachers");
        requestContext.addAttributeToJSP(ALL_UNIVERSITY_TEACHERS_JSP_COLLECTION_ATTRIBUTE, allTeachers);
        return CREATE_TEACHER_CONTEXT;
    }
}

package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;


/**
 * The command shows "teacher" page
 */
public class ShowTeacherPageCommand implements Command {

    private static final Command INSTANCE = new ShowTeacherPageCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowTeacherPageCommand() {

    }

    private static final String TEACHER_PAGE_JSP = "/WEB-INF/jsp/teacher_page.jsp";
    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";


    private static final String CURRENT_USER = "currentUser";
    private static final String USER_COURSE = "userCourse";

    private static final ResponseContext TEACHER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return TEACHER_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession(USER_COURSE);

        Pagination pagination = new PaginationImpl(userCourse.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<CourseDto> usersCourseOnPage = userCourse.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, usersCourseOnPage);
        requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);

        return TEACHER_PAGE_CONTEXT;
    }
}

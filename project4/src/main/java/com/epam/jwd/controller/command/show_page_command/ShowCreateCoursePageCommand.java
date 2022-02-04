package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 * The command shows "create course (by teacher)" page
 */

public class ShowCreateCoursePageCommand implements Command {

    private static final Command INSTANCE = new ShowCreateCoursePageCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String CREATE_COURSE_JSP = "/WEB-INF/jsp/create_course.jsp";
    private static final String TEACHER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";

    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";
    private static final String USER_COURSE = "userCourse";


    private static final ResponseContext TEACHER_CREATE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowCreateCoursePageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession(USER_COURSE);

        ERROR_HANDLER.flushError(requestContext);

        Pagination pagination = new PaginationImpl(userCourse.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<CourseDto> coursesOnPage = userCourse.subList(paginationInfo.get(FROM),paginationInfo.get(TO));

        requestContext.addAttributeToJSP(TEACHER_COURSE_JSP_COLLECTION_ATTRIBUTE, coursesOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);

        return TEACHER_CREATE_COURSE_CONTEXT;
    }
}

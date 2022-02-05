package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 * The command shows "get course teacher" page
 */
public class ShowTeacherCourseCommand implements Command {

    private static final Command INSTANCE = new ShowTeacherCourseCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String TEACHER_GET_COURSE_JSP = "/WEB-INF/jsp/get_course_teacher.jsp";
    private static final String TEACHER_COURSE_FINISHED_JSP_COLLECTION_ATTRIBUTE = "finished_course";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FINISHED_COURSE = "finishedCourse";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";

    private static final ResponseContext TEACHER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return TEACHER_GET_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowTeacherCourseCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> userFinishedCourse = (List<CourseDto>) requestContext.getAttributeFromSession(FINISHED_COURSE);

        ERROR_HANDLER.flushError(requestContext);

        Pagination pagination = new PaginationImpl(userFinishedCourse.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<CourseDto> usersCourseOnPage = userFinishedCourse.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(TEACHER_COURSE_FINISHED_JSP_COLLECTION_ATTRIBUTE, usersCourseOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);

        return TEACHER_PAGE_CONTEXT;
    }
}

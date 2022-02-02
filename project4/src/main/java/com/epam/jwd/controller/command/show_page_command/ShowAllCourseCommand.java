package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 *  The command of getting of all university courses
 */

public class ShowAllCourseCommand implements Command {

    private static final Command INSTANCE = new ShowAllCourseCommand();

    private static final String COURSES_JSP = "/WEB-INF/jsp/all_courses.jsp";

    private static final String COURSES_JSP_COURSES_COLLECTION_ATTRIBUTE = "all_courses";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";


    public static Command getInstance(){
        return INSTANCE;
    }

    private ShowAllCourseCommand(){

    }

    private static final ResponseContext GET_COURSE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return COURSES_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> allCourse =  (List<CourseDto>) requestContext.getAttributeFromSession("allCourses");

        Pagination pagination = new PaginationImpl(allCourse.size());
        int page = pagination.getPage(requestContext);

        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<CourseDto> coursesOnPage = allCourse.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(COURSES_JSP_COURSES_COLLECTION_ATTRIBUTE,coursesOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);
        return GET_COURSE_CONTEXT;
    }
}

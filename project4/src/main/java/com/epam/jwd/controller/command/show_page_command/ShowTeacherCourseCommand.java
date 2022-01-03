package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;

import java.util.List;

/**
 * The command shows "get course teacher" page
 */
public class ShowTeacherCourseCommand implements Command {

    private static final Command INSTANCE = new ShowTeacherCourseCommand();

    private static final String TEACHER_GET_COURSE_JSP = "/WEB-INF/jsp/get_course_teacher.jsp";
    private static final String TEACHER_COURSE_FINISHED_JSP_COLLECTION_ATTRIBUTE = "finished_course";

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
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("finishedCourse");
        requestContext.addAttributeToJSP(TEACHER_COURSE_FINISHED_JSP_COLLECTION_ATTRIBUTE, userCourse);
        return TEACHER_PAGE_CONTEXT;
    }
}

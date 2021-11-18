package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.CourseService;

import java.util.List;
import java.util.NoSuchElementException;

public class DeleteUserCourseCommand implements Command {

    private static final Command INSTANCE = new DeleteUserCourseCommand();
    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String CURRENT_USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";
    private static final String USER_RESULT_JSP = "/WEB-INF/jsp/user_page.jsp";
    private static final String DELETE_COURSE_JSP = "/WEB-INF/jsp/delete_course_user.jsp";
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";

    public static Command getInstance() {
        return INSTANCE;
    }

    private DeleteUserCourseCommand() {

    }

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    private static final ResponseContext USER_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return USER_RESULT_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnDeleteCourse = requestContext.getParameterFromJSP("btnDeleteCourse");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");

        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> courseDtoList = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");

        if (btnDeleteCourse != null){
            //TODO validator for id
            String id = requestContext.getParameterFromJSP("lblDelete");
            int course_id = Integer.parseInt(id);
            CourseDto courseDtoForDelete = courseDtoList.stream()
                    .filter(courseDto -> courseDto.equals(((CourseService)courseService).getById(course_id)))
                    .findFirst()
                    .orElse(null);
            if (courseDtoForDelete == null){
                throw new NoSuchElementException("I can't find this course");
            }
            Boolean result = ((CourseService) courseService).deleteUserFromCourse(courseDtoForDelete,userDto);
            if (!result){
                throw new NoSuchElementException("Something was wrong when we were trying to delete this course.");
            }
            List<CourseDto> user_courses = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(),userDto.getLast_name());
            requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, user_courses);
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, user_courses);
            return REFRESH_PAGE_CONTEXT;
        }else if(btnGetBack !=null){
            requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
            requestContext.addAttributeToJSP(CURRENT_USER_COURSE_JSP_COLLECTION_ATTRIBUTE, courseDtoList);
            return USER_RESULT_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}

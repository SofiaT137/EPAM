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

public class SignUpToCourseCommand implements Command {

    private static final Command INSTANCE = new SignUpToCourseCommand();

    private static final String GET_COURSE_JSP = "/WEB-INF/jsp/get_course_user.jsp";
    private static final String USER_RESULT_JSP = "/WEB-INF/jsp/user_page.jsp";
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE = "possibleCourses";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_COURSE_JSP;
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


    public static Command getInstance(){
        return INSTANCE;
    }

    private SignUpToCourseCommand(){

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnGetCourse = requestContext.getParameterFromJSP("btnGetCourse");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto>possibleCoursesList = (List<CourseDto>) requestContext.getAttributeFromSession("possibleCourses");
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");

        if (btnGetCourse != null){
            String id = requestContext.getParameterFromJSP("lblGet");
            int course_id = Integer.parseInt(id);
            CourseDto courseForAdding = possibleCoursesList.stream()
                    .filter(courseDto -> courseDto.equals(((CourseService)courseService).getById(course_id)))
                    .findFirst()
                    .orElse(null);
            if (courseForAdding == null){
                throw new NoSuchElementException("I can't find this course");
            }
            Boolean result = ((CourseService) courseService).addUserIntoCourse(courseForAdding,userDto);
            if (!result){
                throw new NoSuchElementException("Something was wrong when we were trying to add this user into course.");
            }
            List<CourseDto> courseList = courseService.getAll();
            courseList.remove(courseForAdding);
            List<CourseDto> user_courses = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(),userDto.getLast_name());
            requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, user_courses);
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, user_courses);
            requestContext.addAttributeToSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE, courseList);
            return REFRESH_PAGE_CONTEXT;
        }else if (btnGetBack != null){
            List<CourseDto> user_courses = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(),userDto.getLast_name());
            requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
            requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, user_courses);
            return USER_RESULT_CONTEXT;
        }
        else {
            return DefaultCommand.getInstance().execute(requestContext);
        }
    }
}

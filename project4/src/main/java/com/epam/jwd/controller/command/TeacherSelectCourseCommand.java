package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.ReviewService;
import com.epam.jwd.service.impl.UserService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class TeacherSelectCourseCommand implements Command {

    private static final Command INSTANCE = new TeacherSelectCourseCommand();

    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private static final String GET_RATE_COURSE_COMMAND = "/controller?command=SHOW_RATE_PAGE_COMMAND";
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";
    private static final String SELECTED_COURSES_SESSION_COLLECTION_ATTRIBUTE = "selectedCourse";
    private static final String USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE = "studentsCourse";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";
    private static final String CANNOT_FIND_COURSE_MESSAGE = "This course name is wrong! Or this course does not exist!";
    private final Service<UserDto, Integer> userService = new UserService();
    private final Service<ReviewDto, Integer> reviewService = new ReviewService();

    private final Service<CourseDto, Integer> courseService = new CourseService();


    private static final ResponseContext RATE_STUDENT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_RATE_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext ERROR_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ERROR_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext TEACHER_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return TEACHER_RESULT_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private TeacherSelectCourseCommand() {

    }
    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        String btnFillReview = requestContext.getParameterFromJSP("btnFillReview");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");

        if (btnFillReview != null){
            String course = requestContext.getParameterFromJSP("lblCourseName");
                List<CourseDto> list = ((CourseService) courseService).filterCourse(course);
            if (list.size() != 0){
                CourseDto selectedCourse = list.get(0);
                requestContext.addAttributeToSession(SELECTED_COURSES_SESSION_COLLECTION_ATTRIBUTE,selectedCourse);
                List<UserDto> usersOfSelectedCourse = ((UserService) userService).findALLStudentOnThisCourse(selectedCourse.getName());
                 usersOfSelectedCourse.remove(userDto);
                List<UserDto> list1 = findAllStudentWithReview(usersOfSelectedCourse,selectedCourse);
                usersOfSelectedCourse.removeAll(list1);
                requestContext.addAttributeToSession(USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE,usersOfSelectedCourse);

            }
            else{
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,CANNOT_FIND_COURSE_MESSAGE);
                return ERROR_PAGE_CONTEXT;
            }
            return RATE_STUDENT_CONTEXT;
        }else if(btnGetBack !=null){
            return TEACHER_RESULT_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<UserDto> findAllStudentWithReview(List<UserDto> list, CourseDto current_course){
        List<UserDto> result = new ArrayList<>();
        for (UserDto userDto:
             list) {
            try{
                boolean b = ((ReviewService) reviewService).findReviewByCourseIdAndUserId(current_course.getId(), userDto.getId()) != null;
                result.add(userDto);
            }catch (DAOException exception){
                //log
            }
        }
        return result;
    }
}

package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherPageCommand implements Command {

    private static final Command INSTANCE = new TeacherPageCommand();
    private static final String RATE_STUDENT_COMMAND = "/controller?command=SHOW_TEACHER_COURSE_COMMAND";
    private static final String CREATE_COURSE_COMMAND = "/controller?command=SHOW_CREATE_COURSE_PAGE_COMMAND";
    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_COURSE_PAGE_COMMAND";
    private static final String UPDATE_COURSE_COMMAND = "/controller?command=SHOW_UPDATE_COURSE_PAGE_COMMAND";
    private static final String ALL_USER_SESSION_COLLECTION_ATTRIBUTE = "allUsers";
    private static final String FINISHED_COURSES_SESSION_COLLECTION_ATTRIBUTE = "finishedCourses";
    private final Service<UserDto, Integer> serviceUser = new UserService();

    public static Command getInstance() {
        return INSTANCE;
    }

    private TeacherPageCommand() {

    }

    private static final ResponseContext RATE_STUDENT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return RATE_STUDENT_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext CREATE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext DELETE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext UPDATE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return UPDATE_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");

        String btnRateStudent = requestContext.getParameterFromJSP("btnRateStudent");
        String btnCreateCourse = requestContext.getParameterFromJSP("btnCreateCourse");
        String btnUpdateCourse = requestContext.getParameterFromJSP("btnUpdateCourse");
        String btnDeleteCourse = requestContext.getParameterFromJSP("btnDeleteCourse");

        if (btnRateStudent != null){
            List<UserDto> allUser= serviceUser.getAll();
            requestContext.addAttributeToSession(ALL_USER_SESSION_COLLECTION_ATTRIBUTE,allUser);
            List<CourseDto> finishedCourses = findFinishedCourses(userCourse);
            requestContext.addAttributeToSession(FINISHED_COURSES_SESSION_COLLECTION_ATTRIBUTE,finishedCourses);
            return RATE_STUDENT_CONTEXT;
        }else if (btnCreateCourse != null){
            return CREATE_COURSE_CONTEXT;
        }else if (btnUpdateCourse != null){
            return UPDATE_COURSE_CONTEXT;
        }
        else if(btnDeleteCourse != null){
            return DELETE_COURSE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<CourseDto> findFinishedCourses(List<CourseDto> list){
        List<CourseDto> result;
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Date utilDate = Date.valueOf(tomorrow);
        result = list.stream()
                .filter(courseDto -> courseDto.getEndCourse().before(utilDate))
                .collect(Collectors.toList());
        return result;
    }
}

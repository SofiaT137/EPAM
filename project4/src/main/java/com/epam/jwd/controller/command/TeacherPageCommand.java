package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.UserServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main command of teacher's logic
 */
public class TeacherPageCommand implements Command {

    private static final Command INSTANCE = new TeacherPageCommand();

    private static final String RATE_STUDENT_COMMAND = "/controller?command=SHOW_TEACHER_COURSE_COMMAND";
    private static final String CREATE_COURSE_COMMAND = "/controller?command=SHOW_CREATE_COURSE_PAGE_COMMAND";
    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_COURSE_PAGE_COMMAND";
    private static final String UPDATE_COURSE_COMMAND = "/controller?command=SHOW_UPDATE_COURSE_COMMAND";

    private static final String ALL_USER_SESSION_COLLECTION_ATTRIBUTE = "allUsers";
    private static final String FINISHED_COURSES_SESSION_COLLECTION_ATTRIBUTE = "finishedCourse";
    private static final String NOT_FINISHED_COURSES_SESSION_COLLECTION_ATTRIBUTE = "notFinished";
    private static final String RATE_STUDENT_BUTTON = "btnRateStudent";
    private static final String CREATE_COURSE_BUTTON = "btnCreateCourse";
    private static final String UPDATE_COURSE_BUTTON = "btnUpdateCourse";
    private static final String DELETE_COURSE_BUTTON = "btnDeleteCourse";
    private static final String USER_COURSE = "userCourse";

    private final Service<UserDto, Integer> serviceUser = new UserServiceImpl();

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
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession(USER_COURSE);

        String btnRateStudent = requestContext.getParameterFromJSP(RATE_STUDENT_BUTTON);
        String btnCreateCourse = requestContext.getParameterFromJSP(CREATE_COURSE_BUTTON);
        String btnUpdateCourse = requestContext.getParameterFromJSP(UPDATE_COURSE_BUTTON);
        String btnDeleteCourse = requestContext.getParameterFromJSP(DELETE_COURSE_BUTTON);

        if (btnRateStudent != null){
            List<UserDto> allUser= serviceUser.findAll();
            requestContext.addAttributeToSession(ALL_USER_SESSION_COLLECTION_ATTRIBUTE,allUser);
            List<CourseDto> finishedCourses = findFinishedCourses(userCourse);
            requestContext.addAttributeToSession(FINISHED_COURSES_SESSION_COLLECTION_ATTRIBUTE,finishedCourses);
            return RATE_STUDENT_CONTEXT;
        }else if (btnCreateCourse != null){
            return CREATE_COURSE_CONTEXT;
        }else if (btnUpdateCourse != null){
            List<CourseDto> notFinishedCourses = findNotFinishedCourses(userCourse);
            requestContext.addAttributeToSession(NOT_FINISHED_COURSES_SESSION_COLLECTION_ATTRIBUTE,notFinishedCourses);
            return UPDATE_COURSE_CONTEXT;
        }else if(btnDeleteCourse != null){
            List<CourseDto> notFinishedCourses = findNotFinishedCourses(userCourse);
            requestContext.addAttributeToSession(NOT_FINISHED_COURSES_SESSION_COLLECTION_ATTRIBUTE,notFinishedCourses);
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

    private List<CourseDto> findNotFinishedCourses(List<CourseDto> list){
        List<CourseDto> result;
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Date utilDate = Date.valueOf(tomorrow);
        result = list.stream()
                .filter(courseDto -> courseDto.getEndCourse().after(utilDate))
                .collect(Collectors.toList());
        return result;
    }

}

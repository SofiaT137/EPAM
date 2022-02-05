package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The command of deleting courses
 */
public class DeleteCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteCourseCommand.class);

    private static final Command INSTANCE = new DeleteCourseCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String DELETE_COURSE_BUTTON = "btnDeleteCourse";
    private static final String GET_BACK_BUTTON = "btnGetBack";
    private static final String CURRENT_USER = "currentUser";
    private static final String COURSE_NAME = "courseName";

    private static final String CANNOT_FIND_COURSE_MESSAGE = "cannotFindThisCourse";
    private static final String CANNOT_DELETE_ALL_USER_MESSAGE = "cannotDeleteAllUserFromCourse";
    private static final String YOU_ARE_NOT_THE_MENTOR = "youNotTheMentor";
    private static final String YOU_CANNOT_DELETE_FINISHED_COURSE = "cannotDeleteFinishedCourse";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";

    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_COURSE_PAGE_COMMAND";
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";

    private static final String NOT_FINISHED_SESSION_COLLECTION_ATTRIBUTE = "notFinished";
    private static final String USER_COURSE = "userCourse";

    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();

    public static Command getInstance() {
        return INSTANCE;
    }

    private DeleteCourseCommand() {

    }

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext TEACHER_RESULT_CONTEXT  = new ResponseContext() {

        @Override
        public String getPage() {
            return TEACHER_RESULT_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String btnDeleteCourse = requestContext.getParameterFromJSP(DELETE_COURSE_BUTTON);
        String btnGetBack = requestContext.getParameterFromJSP(GET_BACK_BUTTON);
        UserDto teacher = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);
        String courseName = requestContext.getParameterFromJSP(COURSE_NAME);
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession(USER_COURSE);

        if(btnGetBack !=null){
            return TEACHER_RESULT_CONTEXT;
        }
        if (btnDeleteCourse != null) {
            List<CourseDto> courseList = getCourseListByName(courseName);

            if (courseList.isEmpty()) {
                LOGGER.error(CANNOT_FIND_COURSE_MESSAGE);
                ERROR_HANDLER.setError(CANNOT_FIND_COURSE_MESSAGE, requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            CourseDto courseForDelete = getCourse(courseList);

            if (!isTeacherMentor(teacher, courseForDelete)) {
                ERROR_HANDLER.setError(YOU_ARE_NOT_THE_MENTOR, requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            if (!isCourseFinished(courseForDelete)) {
                ERROR_HANDLER.setError(YOU_CANNOT_DELETE_FINISHED_COURSE, requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            if (!deleteAllUsersFromCourse(courseForDelete)) {
                ERROR_HANDLER.setError(CANNOT_DELETE_ALL_USER_MESSAGE, requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            try {
                deleteCourse(courseForDelete);
            } catch (ServiceException exception) {
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(), requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            List<CourseDto> notFinishedCourse = getNotUserFinishedCourses(teacher);
            requestContext.addAttributeToSession(NOT_FINISHED_SESSION_COLLECTION_ATTRIBUTE, findNotFinishedCourses(notFinishedCourse));
            List<CourseDto> allUserCourse  = getAllUserCourse(teacher);
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, allUserCourse);
        }
        return REFRESH_PAGE_CONTEXT;
    }

    private boolean deleteAllUsersFromCourse(CourseDto courseDtoForDelete){
        return ((CourseServiceImpl)courseService).deleteAllCourseInUserHasCourse(courseDtoForDelete.getId());
    }

    private List<CourseDto> getCourseListByName(String courseName){
        return ((CourseServiceImpl)courseService).filterCourse(courseName);
    }

    private boolean isTeacherMentor(UserDto teacher, CourseDto courseDto){
        List<CourseDto> allUserCourses = ((CourseServiceImpl)courseService).getUserAvailableCourses(teacher.getFirstName(),teacher.getLastName());
        for (CourseDto course:
                allUserCourses) {
            if (courseDto.equals(course)){
                return true;
            }
        }
        return false;
    }

    private boolean isCourseFinished(CourseDto courseDto){
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Date utilDate = Date.valueOf(tomorrow);
        return courseDto.getEndCourse().after(utilDate);
    }
    private CourseDto getCourse(List<CourseDto> allCoursesWithName){
        return allCoursesWithName.get(0);
    }
    private void deleteCourse (CourseDto courseForDelete){
        courseService.delete(courseForDelete);
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

    private List<CourseDto> getNotUserFinishedCourses(UserDto teacher){
        return ((CourseServiceImpl) courseService).getUserAvailableCourses(teacher.getFirstName(),teacher.getLastName());
    }
    private List<CourseDto> getAllUserCourse(UserDto teacher){
        return ((CourseServiceImpl) courseService).getUserAvailableCourses(teacher.getFirstName(),teacher.getLastName());
    }
}

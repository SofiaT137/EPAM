package com.epam.jwd.controller.command;

import com.epam.jwd.dao.exception.DAOException;
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
 * The command of updating course
 */
public class UpdateCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(UpdateCourseCommand.class);
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final Command INSTANCE = new UpdateCourseCommand();

    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_UPDATE_COURSE_COMMAND";
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";

    private static final String CANNOT_FIND_THIS_COURSE_BY_NAME = "cannotFindCourseByName";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String CANNOT_UPDATE_FINISHED_COURSE = "cannotUpdateFinishedCourse";
    private static final String NOT_FINISHED_SESSION_COLLECTION_ATTRIBUTE = "notFinished";
    private static final String YOU_ARE_NOT_THE_MENTOR = "youNotTheMentor";
    private static final String UPDATE_COURSE_BUTTON = "btnUpdate";
    private static final String GET_BACK_BUTTON = "btnGetBack";
    private static final String CURRENT_USER = "currentUser";
    private static final String COURSE_NAME_LABEL = "lblCourseName";
    private static final String START_DATE_LABEL = "lblStartDate";
    private static final String END_DATE_LABEL = "lblEndDate";

    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();

    public static Command getInstance() {
        return INSTANCE;
    }

    private UpdateCourseCommand() {

    }

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REFRESH_PAGE_COMMAND;
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

        String btnUpdate = requestContext.getParameterFromJSP(UPDATE_COURSE_BUTTON);
        String btnGetBack = requestContext.getParameterFromJSP(GET_BACK_BUTTON);
        UserDto teacher = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);

        if(btnGetBack !=null){
            return TEACHER_RESULT_CONTEXT;
        }
        if (btnUpdate != null) {

            String courseName = requestContext.getParameterFromJSP(COURSE_NAME_LABEL);
            Date startDate = Date.valueOf(requestContext.getParameterFromJSP(START_DATE_LABEL));
            Date endDate = Date.valueOf(requestContext.getParameterFromJSP(END_DATE_LABEL));

            List<CourseDto> list = ((CourseServiceImpl) courseService).filterCourse(courseName);

            if (list.isEmpty()){
                ERROR_HANDLER.setError(CANNOT_FIND_THIS_COURSE_BY_NAME,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            CourseDto courseForUpdate = list.get(0);

            if (!checkIfThisTeacherMentor(teacher,courseForUpdate)){
                ERROR_HANDLER.setError(YOU_ARE_NOT_THE_MENTOR,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            if (isCourseFinished(courseForUpdate)){
                ERROR_HANDLER.setError(CANNOT_UPDATE_FINISHED_COURSE,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            try {
               updateCourse(courseName,startDate,endDate,courseForUpdate);
            } catch (ServiceException exception) {
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
            }
            List<CourseDto> notFinishedCourse = getNotUserFinishedCourses(teacher);
            requestContext.addAttributeToSession(NOT_FINISHED_SESSION_COLLECTION_ATTRIBUTE, findNotFinishedCourses(notFinishedCourse));
            List<CourseDto> allUserCourse  = getAllUserCourse(teacher);
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, allUserCourse);
        }

        return REFRESH_PAGE_CONTEXT;
    }

    private void updateCourse(String courseName,Date startDate,Date endDate,CourseDto course){
        course.setName(courseName);
        course.setStartCourse(startDate);
        course.setEndCourse(endDate);
        courseService.update(course);
    }

    private boolean checkIfThisTeacherMentor(UserDto teacher,CourseDto courseDto){
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
        return courseDto.getEndCourse().before(utilDate);
    }

    private List<CourseDto> getNotUserFinishedCourses(UserDto teacher) {
        return ((CourseServiceImpl) courseService).getUserAvailableCourses(teacher.getFirstName(), teacher.getLastName());
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

    private List<CourseDto> getAllUserCourse(UserDto teacher){
        return ((CourseServiceImpl) courseService).getUserAvailableCourses(teacher.getFirstName(),teacher.getLastName());
    }

}

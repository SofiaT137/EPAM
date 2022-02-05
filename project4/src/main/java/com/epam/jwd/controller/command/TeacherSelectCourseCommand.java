package com.epam.jwd.controller.command;

import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The command of selection course for teacher
 */
public class TeacherSelectCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(TeacherSelectCourseCommand.class);
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final Command INSTANCE = new TeacherSelectCourseCommand();

    private static final String GET_RATE_COURSE_COMMAND = "/controller?command=SHOW_RATE_PAGE_COMMAND";
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";
    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_TEACHER_COURSE_COMMAND";

    private static final String SELECTED_COURSES_SESSION_COLLECTION_ATTRIBUTE = "selectedCourse";
    private static final String USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE = "studentsCourse";

    private static final String NO_REVIEWS = "No reviews!";
    private static final String CANNOT_FIND_COURSE_MESSAGE = "cannotFindThisCourse";
    private static final String YOU_ARE_NOT_THE_MENTOR = "youNotTheMentor";
    private static final String THIS_COURSE_IS_UNFINISHED = "thisCourseIsUnfinished";
    private static final String CURRENT_USER = "currentUser";
    private static final String FILL_REVIEW_BUTTON = "btnFillReview";
    private static final String GET_BACK_BUTTON = "btnGetBack";
    private static final String COURSE_NAME = "courseName";



    private final Service<UserDto, Integer> userService = new UserServiceImpl();
    private final Service<ReviewDto, Integer> reviewService = new ReviewServiceImpl();
    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();


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

    public static Command getInstance() {
        return INSTANCE;
    }

    private TeacherSelectCourseCommand() {

    }
    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto teacher = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);
        String btnFillReview = requestContext.getParameterFromJSP(FILL_REVIEW_BUTTON);
        String btnGetBack = requestContext.getParameterFromJSP(GET_BACK_BUTTON);
        String courseName = requestContext.getParameterFromJSP(COURSE_NAME);

        if(btnGetBack !=null){
            return TEACHER_RESULT_CONTEXT;
        }

        if (btnFillReview != null) {

            List<CourseDto> courses = findCoursesWithName(courseName);

            if (courses.isEmpty()){
                ERROR_HANDLER.setError(CANNOT_FIND_COURSE_MESSAGE, requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            CourseDto selectedCourse;

            try{
                selectedCourse = getSelectedCourse(courses);
            }catch (IndexOutOfBoundsException exception){
                ERROR_HANDLER.setError(exception.getMessage(), requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            if (!isTeacherMentor(teacher, selectedCourse)) {
                ERROR_HANDLER.setError(YOU_ARE_NOT_THE_MENTOR, requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            if (!isCourseFinished(selectedCourse)) {
                ERROR_HANDLER.setError(THIS_COURSE_IS_UNFINISHED, requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            requestContext.addAttributeToSession(SELECTED_COURSES_SESSION_COLLECTION_ATTRIBUTE, selectedCourse);

            List<UserDto> usersOfSelectedCourse = findAllStudentsOnTheCourse(selectedCourse,teacher);

            List<UserDto> studentsWithReview = findAllStudentWithReview(usersOfSelectedCourse, selectedCourse);
            if (studentsWithReview.isEmpty()){
                LOGGER.info(NO_REVIEWS);
            }
            usersOfSelectedCourse.removeAll(studentsWithReview);
            requestContext.addAttributeToSession(USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE, usersOfSelectedCourse);
        }

        return RATE_STUDENT_CONTEXT;
    }


    private List<UserDto> findAllStudentWithReview(List<UserDto> list, CourseDto currentCourse){
        List<UserDto> result = new ArrayList<>();
        for (UserDto userDto:
             list) {
                ((ReviewServiceImpl) reviewService).findReviewByCourseIdAndUserId(currentCourse.getId(), userDto.getId());
                result.add(userDto);
        }
        return result;
    }

    private List<CourseDto> findCoursesWithName(String courseName){
        return ((CourseServiceImpl) courseService).filterCourse(courseName);
    }

    private CourseDto getSelectedCourse(List<CourseDto> allCourses){
        return allCourses.get(0);
    }

    private List<UserDto> findAllStudentsOnTheCourse(CourseDto selectedCourse,UserDto teacher){
        List<UserDto> allUserOnCourse = ((UserServiceImpl) userService).findALLStudentOnThisCourse(selectedCourse.getName());
        allUserOnCourse.remove(teacher);
        return allUserOnCourse;
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
        return courseDto.getEndCourse().before(utilDate);
    }
}

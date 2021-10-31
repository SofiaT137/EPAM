package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.model.course.Course;
import com.epam.jwd.repository.model.user.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO implements DAO<Course,Integer> {

    private static final String SQL_SAVE_COURSE = "INSERT INTO course (name, start_date, end_date) VALUES (?, ?, ?)";
    private static final String SQL_ADD_USER_INTO_COURSE = "INSERT INTO user_has_course (user_id, course_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_COURSE_BY_ID = "UPDATE course SET name = ?, start_date = ?, end_date = ? WHERE course_id = ?";
    private static final String SQL_FIND_ALL_COURSE = "SELECT * FROM course";
    private static final String SQL_FIND_COURSE_BY_ID = "SELECT * FROM course WHERE course_id =  ?";
    private static final String SQL_DELETE_COURSE_BY_ID = "DELETE FROM course WHERE course_id = ?";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public Integer save(Course course) {
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COURSE);
            preparedStatement.setString(1,course.getName());
            preparedStatement.setDate(2, (Date) course.getStartCourse());
            preparedStatement.setDate(3,(Date) course.getEndCourse());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int course_id = resultSet.getInt(1);
            course.setId(course_id);
            preparedStatement.close();
            resultSet.close();
            return course_id;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return -1;
        }
    }

    public Boolean addUserIntoCourse(Course course, User user){
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER_INTO_COURSE);
            preparedStatement.setInt(1,course.getId());
            preparedStatement.setInt(2,user.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }


    @Override
    public Boolean update(Course course) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE_BY_ID);
            preparedStatement.setString(1,course.getName());
            preparedStatement.setDate(2, (Date) course.getStartCourse());
            preparedStatement.setDate(3,(Date) course.getEndCourse());
            preparedStatement.setInt(4,course.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }
    @Override
    public Boolean delete(Course course) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE_BY_ID);
            preparedStatement.setInt(1,course.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COURSE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(returnCourse(resultSet));
            }
            preparedStatement.close();
            resultSet.close();
            return courses;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }

    @Override
    public Course findById(Integer id) {
        Course course = null;
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                course=  returnCourse(resultSet);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
        return course;
    }

    private Course returnCourse(ResultSet resultSet) {
        Course course = new Course();
        try {
            course.setId(resultSet.getInt(1));
            course.setName(resultSet.getString(2));
            course.setStartCourse(resultSet.getDate(3));
            course.setEndCourse(resultSet.getDate(4));
        } catch (SQLException e) {
            //logger
        }
        return course;
    }
}

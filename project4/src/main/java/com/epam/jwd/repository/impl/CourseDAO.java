package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.connection_pool.ConnectionPollImpl;
import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.model.course.Course;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseDAO implements DAO<Course,Integer> {

    private static final String SQL_SAVE_COURSE = "INSERT INTO course (course_id, name, start_date, end_date) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_COURSE_BY_ID = "UPDATE course SET name = ?, start_date = ?, end_date = ? WHERE course_id = ?";
    private static final String SQL_FIND_ALL_COURSE = "SELECT * FROM course";
    private static final String SQL_FIND_COURSE_BY_ID = "SELECT * FROM course WHERE course_id =  ?";
    private static final String SQL_DELETE_COURSE_BY_ID = "DELETE FROM course WHERE course_id = ?";


    private ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public Course save(Course course) {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PreparedStatement statement;
        ResultSet resultSet;
        try{
            statement = connection.prepareStatement(SQL_SAVE_COURSE);
            int courseId = statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                course.setId(resultSet.getInt(1));
            }
            statement.setString(2,course.getName());
            statement.setDate(3, (Date) course.getStartCourse());
            statement.setDate(4, (Date) course.getEndCourse());

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionPool.returnConnection(connection);
        }
        return course;
    }


    @Override
    public Boolean update(Course course) {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PreparedStatement statement = null;
        ResultSet resultSet;
        try {
            assert connection != null;
            connection.prepareStatement(SQL_UPDATE_COURSE_BY_ID);
            statement.setInt(1,course.getId());
            statement.setString(2,course.getName());
            statement.setDate(3, (Date) course.getStartCourse());
            statement.setDate(4, (Date) course.getEndCourse());
            return Objects.equals(statement.executeUpdate(),1);
        } catch (SQLException e) {
            //log
            return false;
        }finally {
            connectionPool.returnConnection(connection);
        }
    }
    @Override
    public Boolean delete(Course course) {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            //log here
        }
        PreparedStatement preparedStatement;
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE_BY_ID);
            preparedStatement.setInt(1, course.getId());
            return Objects.equals(preparedStatement.executeUpdate(), 1);
        } catch (SQLException exception) {
            //log
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Course> findAll() {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            //log here
        }
        List<Course> courses = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        try {
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_ALL_COURSE); {
                while (resultSet.next()) {
                    courses.add(returnCourse(resultSet));
                }
            }
        }catch (SQLException e) {
            //todo implement logger and custom exception
        } finally {
            connectionPool.returnConnection(connection);
        }
        return courses;
    }

    @Override
    public Course findById(Integer id) {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try{
            assert connection != null;
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COURSE);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return returnCourse(resultSet);
            }
        } catch (SQLException e) {
            //  logger
        }
        finally {
            connectionPool.returnConnection(connection);
        }

        return null;
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

package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.Dao;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.dao.connection_pool.ConnectionPool;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.model.review.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao implements Dao<Review, Integer> {

    private static final Logger LOGGER = LogManager.getLogger(ReviewDao.class);

    private static final String SQL_SAVE_REVIEW = "INSERT INTO review (user_id, course_id, grade, review) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_REVIEW_BY_ID = "UPDATE review SET user_id = ?, course_id = ?, grade = ? review = ? WHERE review_id = ?";
    private static final String SQL_DELETE_REVIEW_BY_ID = "DELETE FROM review WHERE id = ?";
    private static final String SQL_FIND_ALL_REVIEW = "SELECT * FROM review";
    private static final String SQL_FIND_REVIEW_BY_ID = "SELECT * FROM review WHERE review_id =  ?";
    private static final String SQL_FIND_ACCOUNTS_BY_USER_ID = "SELECT * FROM review WHERE user_id = ?;";
    private static final String SQL_FIND_COURSE_BY_USER_ID_COURSE_ID = "SELECT * FROM review WHERE user_id = ? AND course_id = ? ;";
    private static final String SQL_FIND_REVIEW_BY_COURSE_ID = "SELECT * FROM review WHERE course_id = ? ;";

    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    private static final String ERROR_CANNOT_SAVE_REVIEW = "I cannot create this review!";
    private static final String ERROR_CANNOT_UPDATE_REVIEW = "I cannot update this review!";
    private static final String ERROR_CANNOT_DELETE_REVIEW = "I cannot delete this review!";
    private static final String INFO_CANNOT_FIND_ANY_REVIEW = "I cannot find any review!";
    private static final String ERROR_CANNOT_FIND_ANY_REVIEW_BY_USER_ID = "I cannot find any review by user id!";
    private static final String ERROR_SOMETHING_WRONG_WITH_SQL_REQUEST = "Something wrong with sql request. Check the data!";
    private static final String ERROR_CANNOT_FIND_REVIEW_BY_COURSE_ID_AND_USER_ID = "I can't found this course for this person";
    private static final String ERROR_CANNOT_FIND_REVIEW_BY_COURSE_ID = "I can't found any review by it's course id";


    @Override
    public Integer save(Review review) {
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_REVIEW, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1,review.getUserId());
            preparedStatement.setInt(2,review.getCourseId());
            preparedStatement.setInt(3,review.getGrade());
            preparedStatement.setString(4,review.getComment());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int reviewId = resultSet.getInt(1);
            review.setId(reviewId);
            return reviewId;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_SAVE_REVIEW);
            throw new DAOException(ERROR_CANNOT_SAVE_REVIEW);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Review review) {
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_REVIEW_BY_ID)) {
            preparedStatement.setInt(1,review.getUserId());
            preparedStatement.setInt(2,review.getCourseId());
            preparedStatement.setInt(3,review.getGrade());
            preparedStatement.setString(4,review.getComment());
            preparedStatement.setInt(5,review.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_UPDATE_REVIEW);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Review review) {
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_REVIEW_BY_ID)) {
            preparedStatement.setInt(1,review.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_DELETE_REVIEW);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_REVIEW)){
            ResultSet resultSet = preparedStatement.executeQuery();
            reviews = returnReviewList(resultSet);
            return reviews;
        } catch (SQLException exception) {
            LOGGER.info(INFO_CANNOT_FIND_ANY_REVIEW);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return reviews;
    }

    @Override
    public Review findById(Integer id) {
        Review review;
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_REVIEW_BY_ID)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            review = returnReviewList(resultSet).get(0);
        } catch (SQLException exception) {
            LOGGER.error(INFO_CANNOT_FIND_ANY_REVIEW);
            throw new DAOException(INFO_CANNOT_FIND_ANY_REVIEW);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return review;
    }


    public List<Review> findReviewByUserId(int userId){
        List<Review> reviewList;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNTS_BY_USER_ID)) {
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            reviewList = returnReviewList(resultSet);
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_FIND_ANY_REVIEW_BY_USER_ID);
            throw new DAOException(ERROR_CANNOT_FIND_ANY_REVIEW_BY_USER_ID);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return reviewList;
    }

    public List<Review> findReviewByCourseId(int courseId){
        List<Review> list;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_REVIEW_BY_COURSE_ID)) {
            preparedStatement.setInt(1,courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            list = returnReviewList(resultSet);
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_FIND_REVIEW_BY_COURSE_ID);
            throw new DAOException(ERROR_CANNOT_FIND_REVIEW_BY_COURSE_ID);
        }  finally {
            connectionPool.returnConnection(connection);
        }
        if (list.isEmpty()){
            throw new DAOException(ERROR_CANNOT_FIND_REVIEW_BY_COURSE_ID);
        }
        return list;
    }

    public Review findReviewByCourseIdAndUserId(int courseId,int userId){
        List<Review> list;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_USER_ID_COURSE_ID)) {
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            list = (returnReviewList(resultSet));
            if (!(list.isEmpty())){
                return list.get(0);
            }
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_FIND_REVIEW_BY_COURSE_ID_AND_USER_ID);
            throw new DAOException(ERROR_CANNOT_FIND_REVIEW_BY_COURSE_ID_AND_USER_ID);
        }  finally {
            connectionPool.returnConnection(connection);
        }
        throw new DAOException(ERROR_CANNOT_FIND_REVIEW_BY_COURSE_ID_AND_USER_ID);
    }

    private List<Review> returnReviewList (ResultSet resultSet){
        List<Review> reviewList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("review_id"));
                review.setUserId(resultSet.getInt("user_id"));
                review.setCourseId(resultSet.getInt("course_id"));
                review.setGrade(resultSet.getInt("grade"));
                review.setComment(resultSet.getString("review"));
                reviewList.add(review);
            }
        } catch (SQLException exception) {
            LOGGER.error(ERROR_SOMETHING_WRONG_WITH_SQL_REQUEST);
            throw new DAOException(ERROR_SOMETHING_WRONG_WITH_SQL_REQUEST);
        }
        return reviewList;
    }
}

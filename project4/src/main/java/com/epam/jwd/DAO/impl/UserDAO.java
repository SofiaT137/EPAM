package com.epam.jwd.DAO.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.DAO.connection_pool.api.ConnectionPool;
import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.DAO.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User,Integer> {

    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class);

    private static final String SQL_SAVE_USER = "INSERT INTO user (account_id, university_group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE user_id =  ?";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE user SET account_id = ?, university_group_id = ?, first_name = ?, last_name = ? WHERE user_id = ?";
    private static final String SQL_FIND_USER_BY_FULL_NAME = "SELECT * FROM user WHERE first_name = ? and last_name = ?";
    private static final String SQL_FIND_USER_BY_ACCOUNT_ID = "SELECT * FROM user WHERE account_id = ?";

    private static final String ERROR_CANNOT_SAVE_USER = "I cannot create this user!";
    private static final String ERROR_CANNOT_UPDATE_USER = "I cannot update this user!";
    private static final String ERROR_CANNOT_FIND_ANY_USER = "I cannot find any user!";
    private static final String ERROR_CANNOT_FIND_THIS_USER = "I cannot find this user!";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public Integer save(User user) {
        Connection connection = connectionPool.takeConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setInt(1,user.getAccountId());
                preparedStatement.setInt(2,user.getGroupId());
                preparedStatement.setString(3,user.getFirstName());
                preparedStatement.setString(4,user.getLastName());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int userId = resultSet.getInt(1);
                user.setId(userId);
                resultSet.close();
                return userId;
            } catch (SQLException exception) {
                LOGGER.error(exception.getMessage());
                throw new DAOException(ERROR_CANNOT_SAVE_USER);
            } finally {
                connectionPool.returnConnection(connection);
            }
        }

    @Override
    public Boolean update(User user) {
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID)) {
            preparedStatement.setInt(1,user.getAccountId());
            preparedStatement.setInt(2,user.getGroupId());
            preparedStatement.setString(3,user.getFirstName());
            preparedStatement.setString(4,user.getLastName());
            preparedStatement.setInt(5,user.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_UPDATE_USER);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(User user) {
      return false;
    }

    @Override
    public List<User> findAll() {
        List<User> users;
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            users = returnUserList(resultSet);
            resultSet.close();
            return users;
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_ANY_USER);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public User findById(Integer id) {
            User user;
            Connection connection = connectionPool.takeConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
                preparedStatement.setInt(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                user =  returnUserList(resultSet).get(0);
                resultSet.close();
            } catch (SQLException exception) {
                LOGGER.error(exception.getMessage());
                throw new DAOException(ERROR_CANNOT_FIND_THIS_USER);
            } finally {
                connectionPool.returnConnection(connection);
            }
            return user;
        }

    public List<User> filterUser(String firstName,String lastName){
        List<User> userList;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_FULL_NAME)) {
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            userList = returnUserList(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_THIS_USER);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return userList;
    }

    public User findUserByAccountId(int accountId){
        User user;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ACCOUNT_ID)) {
            preparedStatement.setInt(1,accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = returnUserList(resultSet).get(0);
            resultSet.close();
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_THIS_USER);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    private List<User> returnUserList (ResultSet resultSet){
        List<User> userList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setAccountId(resultSet.getInt("account_id"));
                user.setGroupId(resultSet.getInt("university_group_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                userList.add(user);
            }
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
        }
        return userList;
    }
 }

package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.connection_pool.ConnectionPollImpl;
import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.model.user.Account;
import com.epam.jwd.repository.model.user.User;
import com.epam.jwd.repository.model.group.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO implements DAO<User,Integer> {

    private static final String SQL_SAVE_USER = "INSERT INTO user (user_id, account, group, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO account (account_id, role, password, login) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM user;";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE user_id =  ?";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT * FROM account WHERE account_id =  ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE user_id = ?";
    private static final String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM account WHERE id = ?";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE user SET account = ?, group = ?, first_name = ?, last_name = ? WHERE user_id = ?";
    private static final String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE account SET role_id = ?, password = ?, login = ? WHERE account = ?";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public User save(User user) {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;
        try{
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SAVE_USER);
            int userId = statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            statement.setInt(2,user.getAccount().getId());
            statement.setInt(3,user.getGroup().getId());
            statement.setString(4,user.getFirstname());
            statement.setString(5,user.getLastName());
            saveAccount(connection, user.getAccount());

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public Boolean update(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet;
        try {
            connection = connectionPool.takeConnection();
            connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
            statement.setInt(1,user.getId());
            statement.setObject(2,user.getAccount());
            statement.setObject(3,user.getGroup());
            statement.setString(4,user.getFirstname());
            statement.setString(5,user.getLastName());
            return Objects.equals(statement.executeUpdate(),1)
                    && updateAccountById(connection,user.getAccount());
        } catch (SQLException | InterruptedException e) {
            //log
            return false;
        }finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = connectionPool.takeConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
            preparedStatement.setInt(1, user.getId());
            return Objects.equals(preparedStatement.executeUpdate(), 1)
                    && deleteAccountById(connection, user.getId());
        } catch (SQLException | InterruptedException exception) {
                //log
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<User> findAll() {
        Connection connection = null;
        List<User> users = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        try {
            connection = connectionPool.takeConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_ALL_USERS); {
                while (resultSet.next()) {
                    users.add(returnUser(resultSet));
                }
            }
        }catch (SQLException | InterruptedException e) {
            //todo implement logger and custom exception
         } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

        @Override
    public User findById(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try{
            connection = connectionPool.takeConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return returnUser(resultSet);
            }
        } catch (SQLException | InterruptedException e) {
            //  logger
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return null;
    }

    private User returnUser(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getInt(1));
            user.setAccount((Account) resultSet.getObject(2));
            user.setGroup((Group) resultSet.getObject(3));
            user.setFirstname(resultSet.getString(4));
            user.setLastName(resultSet.getString(5));

        } catch (SQLException e) {
            //logger
        }
        return user;
    }

    private Account returnAccount(ResultSet resultSet) {
        Account account = new Account();
        try {
            account.setId(resultSet.getInt(1));
            account.setRole(resultSet.getInt(2));
            account.setPassword(resultSet.getString(3));
            account.setLogin(resultSet.getString(4));

        } catch (SQLException e) {
            //logger
        }
        return account;
    }

    private void saveAccount(Connection connection, Account account) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT);
            int accountId = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                account.setId(resultSet.getInt(1));
            }
            preparedStatement.setObject(2, account.getRole());
            preparedStatement.setObject(3, account.getPassword());
            preparedStatement.setObject(4, account.getLogin());
    }

    private Boolean deleteAccountById(Connection connection, Integer user_id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, user_id);
        return Objects.equals(preparedStatement.executeUpdate(), 1);
    }

    private Boolean updateAccountById(Connection connection , Account account) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID);
        preparedStatement.setInt(1,account.getRole());
        preparedStatement.setString(2,account.getLogin());
        preparedStatement.setString(3,account.getPassword());
        preparedStatement.setInt(4,account.getId());
        return Objects.equals(preparedStatement.executeUpdate(),1);
    }

    private Account findAccountByUserId(Connection connection, int account_id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID);
        preparedStatement.setInt(1,account_id);
        ResultSet resultSet  = preparedStatement.executeQuery();
        if (resultSet.next()){
            return returnAccount(resultSet);
        }
        else {
            throw new SQLException();
        }
    }
 }

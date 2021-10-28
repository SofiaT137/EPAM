package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.connection_pool.ConnectionPollImpl;
import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.model.course.Course;
import com.epam.jwd.repository.model.user.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account,Integer> {

    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO account (role_id, login, password) VALUES (?, ?, ?)";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT * FROM account WHERE account_id =  ?";
    private static final String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM account WHERE account_id = ?";
    private static final String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE user SET role_id, login = ?, password = ? WHERE account_id = ?";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public Account save(Account account) {
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT);
            preparedStatement.setInt(1, account.getRole_id());
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int account_id = resultSet.getInt(1);
            account.setId(account_id);
            preparedStatement.close();
            resultSet.close();
            return account;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }

    @Override
    public Boolean update(Account account) {
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID);
            preparedStatement.setInt(1, account.getRole_id());
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setInt(5, account.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }

    @Override
    public Boolean delete(Account account) {
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_ID);
            preparedStatement.setInt(1, account.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ACCOUNTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(returnAccount(resultSet));
            }
            preparedStatement.close();
            resultSet.close();
            return accounts;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }

        @Override
        public Account findById (Integer id){
            Account account = null;
            try(Connection connection = connectionPool.takeConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID);
                preparedStatement.setInt(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    account = returnAccount(resultSet);
                }
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException | InterruptedException exception) {
                //TODO log and throw exception;
                return null;
            }
            return account;
        }

        private Account returnAccount (ResultSet resultSet){
            Account account = new Account();
            try {
                account.setId(resultSet.getInt(1));
                account.setRole_id(resultSet.getInt(2));
                account.setLogin(resultSet.getString(3));
                account.setPassword(resultSet.getString(4));
            } catch (SQLException e) {
                //logger
            }
            return account;
        }
    }



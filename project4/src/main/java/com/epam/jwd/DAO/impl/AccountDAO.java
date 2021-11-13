package com.epam.jwd.DAO.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.DAO.connection_pool.api.ConnectionPool;
import com.epam.jwd.DAO.model.user.Account;
import com.epam.jwd.DAO.model.user.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account, Integer>  {

    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO account (role_id, login, password) VALUES (?, ?, ?)";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT * FROM account WHERE account_id =  ?";
    private static final String SQL_FIND_ACCOUNTS_BY_LOGIN = "SELECT * FROM account WHERE login = ?;";
    private static final String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM account WHERE account_id = ?";
    private static final String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE user SET role_id, login = ?, password = ? WHERE account_id = ?";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    private final RoleDAO roleDAO = new RoleDAO();

    public Account createAccount(Role role, String login,String password){
        return new Account(role,login,password);
    }

    @Override
    public Integer save(Account account) {
        try (Connection connection = connectionPool.takeConnection()) {
            ResultSet resultSet;
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, roleDAO.getIdByRoleName(account.getRole().getName()));
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int account_id = resultSet.getInt(1);
            account.setId(account_id);
            preparedStatement.close();
            resultSet.close();
            return account_id;
        } catch (SQLException | InterruptedException exception) {
            //log and Rethrow exception
            return -1;
        }
    }

    @Override
    public Boolean update(Account account) {
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID);
            preparedStatement.setObject(1, account.getRole());
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
        List<Account> accounts;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ACCOUNTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            accounts = returnAccountList(resultSet);
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
        Account account;
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            account = returnAccountList(resultSet).get(0);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
        return account;
    }

    public List<Account> filterAccount(String login){
        List<Account> accountList;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNTS_BY_LOGIN);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            accountList = returnAccountList(resultSet);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
        return accountList;
    }

    private List<Account> returnAccountList (ResultSet resultSet){
        List<Account> accountList = new ArrayList<>();
        try {
            if (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("account_id"));
                account.setRole(roleDAO.getRoleById(resultSet.getInt("role_id")));
                account.setLogin(resultSet.getString("login"));
                account.setPassword(resultSet.getString("password"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }
}



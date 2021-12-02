package com.epam.jwd.DAO.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.DAO.connection_pool.api.ConnectionPool;
import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.DAO.model.user.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account, Integer>  {

    private static final Logger LOGGER = LogManager.getLogger(AccountDAO.class);

    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO account (role_id, login, password, is_active) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT * FROM account WHERE account_id =  ?";
    private static final String SQL_FIND_ACCOUNTS_BY_LOGIN_AND_PASSWORD = "SELECT * FROM account WHERE login = ? and password = ? ";
    private static final String SQL_FIND_ACCOUNTS_BY_LOGIN = "SELECT * FROM account WHERE login = ?";
    private static final String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM account WHERE account_id = ?";
    private static final String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE account SET role_id = ?, login = ?, password = ?, is_active = ? WHERE account_id = ?";

    private static final String ERROR_CANNOT_SAVE_ACCOUNT = "I cannot create this account!";
    private static final String ERROR_CANNOT_UPDATE_ACCOUNT= "I cannot update this account!";
    private static final String ERROR_CANNOT_DELETE_ACCOUNT = "I cannot delete this account!";
    private static final String ERROR_CANNOT_FIND_ANY_ACCOUNT = "I cannot find any account!";
    private static final String ERROR_CANNOT_FIND_ACCOUNT_BY_LOGIN = "I cannot find this account by it's login";
    private static final String ERROR_CANNOT_FIND_ACCOUNT_BY_LOGIN_AND_PASSWORD = "I cannot find this account by it's login and password!";
    private static final String ERROR_NOT_UNIQUE_ACCOUNT = "This account is not unique!";
    private static final String ERROR_SOMETHING_WRONG_WITH_SQL_REQUEST = "Something wrong with sql request. Check the data!";

    private final RoleDAO roleDAO = new RoleDAO();


    @Override
    public Integer save(Account account) {
        try (Connection connection = connectionPool.takeConnection()) {
            ResultSet resultSet;
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, roleDAO.getIdByRoleName(account.getRole().getName()));
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setInt(4, account.getIsActive());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int account_id = resultSet.getInt(1);
            account.setId(account_id);
            preparedStatement.close();
            resultSet.close();
            return account_id;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(exception.getMessage());
        }
        throw new DAOException(ERROR_CANNOT_SAVE_ACCOUNT);
    }

    @Override
    public Boolean update(Account account) {
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID);
            preparedStatement.setInt(1, roleDAO.getIdByRoleName(account.getRole().getName()));
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setInt(4, account.getIsActive());
            preparedStatement.setInt(5, account.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(ERROR_CANNOT_UPDATE_ACCOUNT);
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
            LOGGER.error(ERROR_CANNOT_DELETE_ACCOUNT);
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
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_ANY_ACCOUNT);
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
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_ANY_ACCOUNT);
        }
        return account;
    }

    public Account filterAccount(String login,String password){
        List<Account> accountList;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNTS_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            accountList = returnAccountList(resultSet);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_ACCOUNT_BY_LOGIN_AND_PASSWORD);
        }
        if (accountList.size() != 1){
            LOGGER.error(ERROR_NOT_UNIQUE_ACCOUNT);
            throw new DAOException(ERROR_NOT_UNIQUE_ACCOUNT);
        }
        return accountList.get(0);
    }

    private List<Account> returnAccountList (ResultSet resultSet){
        List<Account> accountList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("account_id"));
                account.setRole(roleDAO.getRoleById(resultSet.getInt("role_id")));
                account.setLogin(resultSet.getString("login"));
                account.setPassword(resultSet.getString("password"));
                account.setIsActive(resultSet.getInt("is_active"));
                accountList.add(account);
            }
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_SOMETHING_WRONG_WITH_SQL_REQUEST);
        }
        return accountList;
    }

    public Account findAccountByLogin(String login){
        List<Account> accountList;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNTS_BY_LOGIN);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            accountList = returnAccountList(resultSet);
            if (accountList.size() == 0){
                throw new DAOException(ERROR_CANNOT_FIND_ACCOUNT_BY_LOGIN);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_ACCOUNT_BY_LOGIN);
        }
        return accountList.get(0);
    }
}



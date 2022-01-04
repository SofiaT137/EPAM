package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.Dao;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.dao.connection_pool.ConnectionPool;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.model.user.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class AccountDaoImpl implements Dao<Account, Integer> {

    private static final Logger LOGGER = LogManager.getLogger(AccountDaoImpl.class);

    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();
    private final RoleDaoImpl roleDAOImpl = new RoleDaoImpl();

    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO account (role_id, login, password, is_active) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT * FROM account WHERE account_id =  ?";
    private static final String SQL_FIND_ACCOUNTS_BY_LOGIN = "SELECT * FROM account WHERE login = ?";
    private static final String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM account WHERE account_id = ?";
    private static final String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE account SET role_id = ?, login = ?, password = ?, is_active = ? WHERE account_id = ?";

    private static final String ERROR_CANNOT_SAVE_ACCOUNT = "I cannot create this account!";
    private static final String ERROR_CANNOT_UPDATE_ACCOUNT= "I cannot update this account!";
    private static final String ERROR_CANNOT_DELETE_ACCOUNT = "I cannot delete this account!";
    private static final String ERROR_CANNOT_FIND_THIS_ACCOUNT = "I cannot find this account!";
    private static final String ERROR_CANNOT_FIND_ACCOUNT_BY_LOGIN = "I cannot find this account by it's login";
    private static final String ERROR_PASSWORD_IS_INCORRECT = "The password is incorrect!";
    private static final String ERROR_NOT_UNIQUE_ACCOUNT = "This account is not unique!";
    private static final String ERROR_SOMETHING_WRONG_WITH_SQL_REQUEST = "Something wrong with sql request. Check the data!";


    @Override
    public Integer save(Account account) {
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, roleDAOImpl.getIdByRoleName(account.getRole().getName()));
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3,account.getPassword());
            preparedStatement.setInt(4, account.getIsActive());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int accountId = resultSet.getInt(1);
            account.setId(accountId);
            return accountId;
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_SAVE_ACCOUNT + ": " + exception.getMessage() );
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Account account) {
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1, roleDAOImpl.getIdByRoleName(account.getRole().getName()));
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setInt(4, account.getIsActive());
            preparedStatement.setInt(5, account.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_UPDATE_ACCOUNT);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Account account){
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1, account.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_DELETE_ACCOUNT);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Account> findAll(){
        List<Account> accounts;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ACCOUNTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            accounts = returnAccountList(resultSet);
            return accounts;
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_THIS_ACCOUNT);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Account findById (Integer id){
        Account account;
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            account = returnAccountFromDataBase(resultSet);
        } catch (SQLException exception){
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_THIS_ACCOUNT);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return account;
    }

    public Account findAccountByLogin(String login){
        Account account;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNTS_BY_LOGIN)) {
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            account = returnAccountFromDataBase(resultSet);
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            throw new DAOException(ERROR_CANNOT_FIND_ACCOUNT_BY_LOGIN);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return account;
    }

    private List<Account> returnAccountList (ResultSet resultSet){
        List<Account> accountList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("account_id"));
                account.setRole(roleDAOImpl.getRoleById(resultSet.getInt("role_id")));
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

    public Account findAccountByLoginAndPassword(String login, String password){
        Account account = findAccountByLogin(login);
        if (!(BCrypt.checkpw(password,account.getPassword()))){
            throw new DAOException(ERROR_PASSWORD_IS_INCORRECT);
        }
        return account;
    }

    private Account returnAccountFromDataBase(ResultSet resultSet){
        List<Account> list = (returnAccountList(resultSet));
        if (list.isEmpty()){
            throw new DAOException(ERROR_CANNOT_FIND_THIS_ACCOUNT);
        }
        else if (list.size() > 1){
            throw new DAOException(ERROR_NOT_UNIQUE_ACCOUNT);
        }
        return list.get(0);
    }
}



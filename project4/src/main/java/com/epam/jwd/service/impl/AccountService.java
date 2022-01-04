package com.epam.jwd.service.impl;

import com.epam.jwd.dao.Dao;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.model.user.Account;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.converter.impl.AccountConverter;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.impl.AccountValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

/**
 * The account service
 */
public class AccountService implements Service<AccountDto,Integer> {

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    private final Dao<Account,Integer> accountDao = new AccountDaoImpl();
    private final Validator<AccountDto> accountValidator = new AccountValidator();
    private final Converter<Account, AccountDto, Integer> accountConverter = new AccountConverter();

    private static final String ID_IS_NULL_EXCEPTION = "This id is null";
    private static final String ACCOUNT_NOT_FOUND_EXCEPTION = "This account is not found!";
    private static final String REPOSITORY_IS_EMPTY_EXCEPTION = "Repository is empty. I can't find any account.";

    public void validate(String login, String password){
        AccountDto dummy = new AccountDto();
        dummy.setLogin(login);
        dummy.setPassword(password);
        accountValidator.validate(dummy);
    }

    /**
     * Encrypt password method
     * @param password password for encrypt
     * @return encrypted password
     */
    public String encryptPassword(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    @Override
    public AccountDto create(AccountDto value) throws ServiceException {
        Account account = accountConverter.convert(value);
        accountDao.save(account);
        return accountConverter.convert(account);
    }

    @Override
    public Boolean update(AccountDto value) throws ServiceException {
        return accountDao.update(accountConverter.convert(value));
    }

    @Override
    public Boolean delete(AccountDto value) throws ServiceException {
        return accountDao.delete(accountConverter.convert(value));
    }

    @Override
    public AccountDto getById(Integer id) throws ServiceException {
        if (id == null) {
            LOGGER.error(ID_IS_NULL_EXCEPTION);
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
        Account account =  accountDao.findById(id);
        if (account == null){
            LOGGER.error(ACCOUNT_NOT_FOUND_EXCEPTION);
            throw new ServiceException(ACCOUNT_NOT_FOUND_EXCEPTION);
        }
        return accountConverter.convert(account);
    }

    @Override
    public List<AccountDto> findAll() throws ServiceException {
        List<Account> daoGetAll = accountDao.findAll();
        List<AccountDto> accountDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            LOGGER.error(REPOSITORY_IS_EMPTY_EXCEPTION);
            throw new ServiceException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        daoGetAll.forEach(account -> accountDtoList.add(accountConverter.convert(account)));
        return accountDtoList;
    }

    /**
     * Find account method by login and password
     * @param login
     * @param password
     * @return AccountDto Object
     */
    public AccountDto filterAccount(String login,String password) {
        Account account = ((AccountDaoImpl) accountDao).findAccountByLoginAndPassword(login,password);
        return accountConverter.convert(account);
    }

    /**
     * Find account method by login
     * @param login
     * @return AccountDto Object
     */
    public AccountDto getAccount(String login) {
        Account account = ((AccountDaoImpl) accountDao).findAccountByLogin(login);
        return accountConverter.convert(account);
    }
}

package com.epam.jwd.service.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.impl.AccountDAO;
import com.epam.jwd.DAO.model.user.Account;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.AccountConverter;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.AccountValidator;


import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

public class AccountService implements Service<AccountDto,Integer> {

    private final DAO<Account,Integer> accountDAO = new AccountDAO();
    private final Validator<AccountDto> accountValidator = new AccountValidator();
    private final Converter<Account, AccountDto, Integer> accountConverter = new AccountConverter();

    private static final String ID_IS_NULL_EXCEPTION = "This id is null";
    private static final String ACCOUNT_NOT_FOUND_EXCEPTION = "This account is not found!";
    private static final String REPOSITORY_IS_EMPTY_EXCEPTION = "Repository is empty. I can't find any account.";
    private static final String CANNOT_FIND_ACCOUNT_EXCEPTION = "I can't find this account by its name";

    @Override
    public AccountDto create(AccountDto value) throws ServiceException {
        accountValidator.validate(value);
        Account account = accountConverter.convert(value);
        accountDAO.save(account);
        return accountConverter.convert(account);
    }

    @Override
    public Boolean update(AccountDto value) throws ServiceException, ServerException {
        accountValidator.validate(value);
        return accountDAO.update(accountConverter.convert(value));
    }

    @Override
    public Boolean delete(AccountDto value) throws ServiceException, ServerException {
        accountValidator.validate(value);
        return accountDAO.delete(accountConverter.convert(value));
    }

    @Override
    public AccountDto getById(Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
        Account account =  accountDAO.findById(id);
        if (account == null){
            throw new ServiceException(ACCOUNT_NOT_FOUND_EXCEPTION);
        }
        return accountConverter.convert(account);
    }

    @Override
    public List<AccountDto> getAll() throws ServiceException {
        List<Account> daoGetAll = accountDAO.findAll();
        List<AccountDto> accountDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            throw new ServiceException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        daoGetAll.forEach(account -> accountDtoList.add(accountConverter.convert(account)));
        return accountDtoList;
    }

    public List<AccountDto> filterAccount(String login,String password){
        List<Account> daoGetAll = ((AccountDAO)accountDAO).filterAccount(login,password);
        List<AccountDto> accountDtoList = new ArrayList<>();
        daoGetAll.forEach(account -> accountDtoList.add(accountConverter.convert(account)));
        return accountDtoList;
    }
}

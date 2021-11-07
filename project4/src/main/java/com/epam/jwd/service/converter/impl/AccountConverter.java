package com.epam.jwd.service.converter.impl;

import com.epam.jwd.DAO.model.user.Account;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.AccountDto;

public class AccountConverter implements Converter<Account, AccountDto,Integer> {

    @Override
    public Account convert(AccountDto accountDto) {
        return new Account(accountDto.getId(),accountDto.getRole_id(),accountDto.getLogin(),accountDto.getPassword());
    }

    @Override
    public AccountDto convert(Account account) {
        return new AccountDto(account.getId(),account.getRole_id(),account.getLogin(),account.getPassword());
    }
}

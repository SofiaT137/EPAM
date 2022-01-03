package com.epam.jwd.service.converter.impl;

import com.epam.jwd.Dao.model.user.Account;
import com.epam.jwd.Dao.model.user.Role;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.AccountDto;

/**
 * Class for convert of account
 */
public class AccountConverter implements Converter<Account, AccountDto,Integer> {


    @Override
    public Account convert(AccountDto accountDto) {
        return new Account(accountDto.getId(), Role.getByName(accountDto.getRole()),accountDto.getLogin(),accountDto.getPassword(),accountDto.getIsActive());
    }

    @Override
    public AccountDto convert(Account account) {
        return new AccountDto(account.getId(),account.getRole().getName(),account.getLogin(),account.getPassword(),account.getIsActive());
    }
}

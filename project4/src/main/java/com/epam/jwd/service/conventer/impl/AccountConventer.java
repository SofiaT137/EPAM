package com.epam.jwd.service.conventer.impl;

import com.epam.jwd.repository.model.user.Account;
import com.epam.jwd.service.conventer.api.Conventer;
import com.epam.jwd.service.dto.userdto.AccountDto;

public class AccountConventer implements Conventer<Account, AccountDto,Integer> {

    @Override
    public Account convert(AccountDto accountDto) {
        return new Account(accountDto.getId(),accountDto.getRole_id(),accountDto.getLogin(),accountDto.getPassword());
    }

    @Override
    public AccountDto convert(Account account) {
        return new AccountDto(account.getId(),account.getRole_id(),account.getLogin(),account.getPassword());
    }
}

package com.bank.danamon.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bank.danamon.models.AccountModel;
import com.bank.danamon.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountModel save(AccountModel accountModel) {
        return accountRepository.save(accountModel);
    }

    public AccountModel findOne(String account_id) {
        Optional<AccountModel> accountModel = accountRepository.findByUid(account_id);
        if (!accountModel.isPresent()) {
            return null;
        }
        return accountModel.get();
    }

    public AccountModel findByUserId(String user_id) {
        Optional<AccountModel> accountModel = accountRepository.findByUserId(user_id);
        if (!accountModel.isPresent()) {
            return null;
        }
        return accountModel.get();
    }

    public AccountModel softDelete(AccountModel accountModel) {
        accountRepository.save(accountModel);
        return accountModel;
    }

    public List<AccountModel> findAll() {
        List<AccountModel> aList = accountRepository.findAll();
        if (aList.isEmpty()) {
            aList = null;
        }
        return aList;
    }

    public Page<AccountModel> search(String keyword, Pageable pageable) {
        return accountRepository.search(keyword, pageable);
    }
}

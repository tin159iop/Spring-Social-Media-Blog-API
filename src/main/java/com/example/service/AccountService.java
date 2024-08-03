package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import com.example.exception.UserExistedException;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public Account register(Account account) throws Exception, UserExistedException {
        if (account.getUsername().length() == 0) {
            throw new Exception("Username cannot be blank!");
        }
        if (account.getPassword().length() < 4) {
            throw new Exception("Password too short!");
        }

        Account exist = this.accountRepository.findByUsername(account.getUsername());

        if (exist != null) {
            throw new UserExistedException("Username already exists!");
        }
        
        return this.accountRepository.save(account);
    }
    
    public Account login(Account account){
        return this.accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}

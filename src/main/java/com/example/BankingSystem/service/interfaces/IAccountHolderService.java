package com.example.BankingSystem.service.interfaces;

import com.example.BankingSystem.controller.dto.AccountHolderDTO;
import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.model.User;

public interface IAccountHolderService {
    AccountHolder store(AccountHolderDTO accountHolderDTO);
    void update(Integer id, AccountHolderDTO accountHolderDTO);
    void delete(Integer id);
}

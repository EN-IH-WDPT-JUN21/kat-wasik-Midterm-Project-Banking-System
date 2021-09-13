package com.example.BankingSystem.service.interfaces;

import com.example.BankingSystem.controller.dto.AccountHolderDTO;
import com.example.BankingSystem.model.AccountHolder;

public interface IAccountHolderService {
    public AccountHolder store(AccountHolderDTO accountHolderDTO);
}

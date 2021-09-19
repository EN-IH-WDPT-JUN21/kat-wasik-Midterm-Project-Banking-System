package com.example.BankingSystem.service.interfaces;

import com.example.BankingSystem.controller.dto.AccountDTO;
import com.example.BankingSystem.controller.dto.BalanceDTO;
import com.example.BankingSystem.controller.dto.StatusDTO;
import com.example.BankingSystem.model.Account;
import com.example.BankingSystem.model.Money;

public interface IAccountService {
    Account store(AccountDTO accountDTO);
    Money getBalance(Integer id);
    void update(Integer id, AccountDTO accountDTO);
    void updateStatus(Integer id, StatusDTO statusDTO);
    void updateBalance(Integer id, BalanceDTO balanceDTO);
    void delete(Integer id);
}

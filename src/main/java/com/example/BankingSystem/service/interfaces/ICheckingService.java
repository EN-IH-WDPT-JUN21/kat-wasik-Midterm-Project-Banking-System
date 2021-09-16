package com.example.BankingSystem.service.interfaces;

import com.example.BankingSystem.controller.dto.BalanceDTO;
import com.example.BankingSystem.controller.dto.CheckingDTO;
import com.example.BankingSystem.controller.dto.StatusDTO;
import com.example.BankingSystem.model.Checking;

import javax.validation.Valid;

public interface ICheckingService {
    Checking store(CheckingDTO checkingDTO);
    void update(Integer id, CheckingDTO checkingDTO);
    void updateStatus(Integer id, StatusDTO statusDTO);
    void updateBalance(Integer id, BalanceDTO balanceDTO);
    void delete(Integer id);
}

package com.example.BankingSystem.service.interfaces;

import com.example.BankingSystem.controller.dto.CheckingDTO;
import com.example.BankingSystem.model.Checking;

public interface ICheckingService {
    Checking store(CheckingDTO checkingDTO);
}

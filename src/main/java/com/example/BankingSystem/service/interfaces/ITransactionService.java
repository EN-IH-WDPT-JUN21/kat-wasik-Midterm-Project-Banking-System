package com.example.BankingSystem.service.interfaces;

import com.example.BankingSystem.controller.dto.TransactionDTO;
import com.example.BankingSystem.model.Transaction;

public interface ITransactionService {
    Transaction store(TransactionDTO transactionDTO, String username);
    void delete(Integer id);
}

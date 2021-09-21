package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.TransactionDTO;
import com.example.BankingSystem.controller.interfaces.ITransactionController;
import com.example.BankingSystem.model.Transaction;
import com.example.BankingSystem.repository.TransactionRepository;
import com.example.BankingSystem.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionController implements ITransactionController {
    @Autowired
    ITransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;

    // CREATE
    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction addTransaction(@RequestBody @Valid TransactionDTO transactionDTO, Authentication authentication) {
        String username = authentication.getName();

        return transactionService.store(transactionDTO, username);
    }

    // READ

    // UPDATE

    // DELETE
}

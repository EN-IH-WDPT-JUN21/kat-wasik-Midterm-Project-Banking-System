package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.TransactionDTO;
import com.example.BankingSystem.controller.interfaces.ITransactionController;
import com.example.BankingSystem.model.Transaction;
import com.example.BankingSystem.repository.TransactionRepository;
import com.example.BankingSystem.service.interfaces.ITransactionService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/transaction")
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @GetMapping("/transaction/{id}")
    public Transaction getById(@PathVariable Integer id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);

        return transactionOptional.isPresent() ? transactionOptional.get() : null;
    }

    // UPDATE

    // DELETE
    @DeleteMapping("/transaction/{id}")
    public void delete(@PathVariable Integer id) {
        transactionService.delete(id);
    }
}

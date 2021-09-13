package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.AccountHolderDTO;
import com.example.BankingSystem.controller.interfaces.IAccountHolderController;
import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.repository.AccountHolderRepository;
import com.example.BankingSystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountHolderController implements IAccountHolderController {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    IAccountHolderService accountHolderService;

    // CREATE
    @PostMapping("/accountholder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addNewAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        return accountHolderService.store(accountHolderDTO);
    }

    // READ

    // UPDATE

    // DELETE
}

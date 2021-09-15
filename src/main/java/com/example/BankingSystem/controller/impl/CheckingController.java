package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.CheckingDTO;
import com.example.BankingSystem.controller.interfaces.ICheckingController;
import com.example.BankingSystem.model.Checking;
import com.example.BankingSystem.repository.CheckingRepository;
import com.example.BankingSystem.service.interfaces.ICheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CheckingController implements ICheckingController {
    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    ICheckingService checkingService;

    // CREATE
    @PostMapping("/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Checking addNewChecking(@RequestBody @Valid CheckingDTO checkingDTO) {
        return checkingService.store(checkingDTO);
    }

    // READ

    // UPDATE

    // DELETE
}

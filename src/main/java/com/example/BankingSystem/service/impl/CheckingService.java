package com.example.BankingSystem.service.impl;

import com.example.BankingSystem.controller.dto.CheckingDTO;
import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.model.Checking;
import com.example.BankingSystem.model.Money;
import com.example.BankingSystem.repository.AccountHolderRepository;
import com.example.BankingSystem.repository.CheckingRepository;
import com.example.BankingSystem.service.interfaces.ICheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CheckingService implements ICheckingService {
    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public Checking store(CheckingDTO checkingDTO) {
        Checking newChecking = new Checking();

        newChecking.setBalance(new Money(new BigDecimal(checkingDTO.getBalance())));
        newChecking.setSecretKey(checkingDTO.getSecretKey());

        Integer primaryOwnerId = Integer.parseInt(checkingDTO.getPrimaryOwnerId());
        Optional<AccountHolder> primaryOwner = accountHolderRepository.findById(primaryOwnerId);
        if (primaryOwner.isPresent()) {
            newChecking.setPrimaryOwner(primaryOwner.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Account Holder with id " + primaryOwnerId + " does not exist.");
        }

        if (checkingDTO.getSecondaryOwnerId() != null) {
            Integer secondaryOwnerId = Integer.parseInt(checkingDTO.getSecondaryOwnerId());
            Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId);
            if (secondaryOwner.isPresent()) {
                newChecking.setSecondaryOwner(secondaryOwner.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Account Holder with id " + secondaryOwnerId + " does not exist.");
            }
        }

        if (!checkingRepository.findAll().contains(newChecking)) {
            return checkingRepository.save(newChecking);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Checking account already exists in the system.");
        }
    }
}
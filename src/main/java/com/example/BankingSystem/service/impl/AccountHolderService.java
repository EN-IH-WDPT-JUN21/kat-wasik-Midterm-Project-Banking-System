package com.example.BankingSystem.service.impl;

import com.example.BankingSystem.controller.dto.AccountHolderDTO;
import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.model.Address;
import com.example.BankingSystem.repository.AccountHolderRepository;
import com.example.BankingSystem.repository.AddressRepository;
import com.example.BankingSystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AccountHolderService implements IAccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AddressRepository addressRepository;

    public AccountHolder store(AccountHolderDTO accountHolderDTO) {
        AccountHolder newAccountHolder = new AccountHolder();

        newAccountHolder.setName(accountHolderDTO.getName());

        LocalDate dateOfBirth = LocalDate.parse(accountHolderDTO.getDateOfBirth());
        if (dateOfBirth.isBefore(LocalDate.now())) {
            newAccountHolder.setDateOfBirth(dateOfBirth);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The date of birth cannot be in the future.");
        }

        Integer primaryAddressId = Integer.parseInt(accountHolderDTO.getPrimaryAddressId());
        Optional<Address> primaryAddress = addressRepository.findById(primaryAddressId);
        if (primaryAddress.isPresent()) {
            newAccountHolder.setPrimaryAddress(primaryAddress.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The address with id " + primaryAddressId + " does not exist.");
        }

        if (accountHolderDTO.getMailingAddressId() != null) {
            Integer mailingAddressId = Integer.parseInt(accountHolderDTO.getMailingAddressId());
            Optional<Address> mailingAddress = addressRepository.findById(mailingAddressId);
            if (mailingAddress.isPresent()) {
                newAccountHolder.setMailingAddress(mailingAddress.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The address with id " + mailingAddressId + " does not exist.");
            }
        }

        if (!accountHolderRepository.findAll().contains(newAccountHolder)) {
            return accountHolderRepository.save(newAccountHolder);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account holder already exists in the system.");
        }
    }
}

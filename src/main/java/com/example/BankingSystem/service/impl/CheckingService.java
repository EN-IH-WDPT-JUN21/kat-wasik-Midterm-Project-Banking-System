package com.example.BankingSystem.service.impl;

import com.example.BankingSystem.controller.dto.BalanceDTO;
import com.example.BankingSystem.controller.dto.CheckingDTO;
import com.example.BankingSystem.controller.dto.StatusDTO;
import com.example.BankingSystem.enums.Status;
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

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
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

    public Money getBalance(Integer id) {
        Optional<Checking> storedChecking = checkingRepository.findById(id);

        if (storedChecking.isPresent()) {
            return storedChecking.get().getBalance();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checking account with id " + id + " does not exist.");
        }
    }


    public void update(Integer id, CheckingDTO checkingDTO) {
        Optional<Checking> storedChecking = checkingRepository.findById(id);

        if (storedChecking.isPresent()) {
            storedChecking.get().setBalance(new Money(new BigDecimal(checkingDTO.getBalance())));
            storedChecking.get().setSecretKey(checkingDTO.getSecretKey());

            Integer primaryOwnerId = Integer.parseInt(checkingDTO.getPrimaryOwnerId());
            Optional<AccountHolder> primaryOwner = accountHolderRepository.findById(primaryOwnerId);
            if (primaryOwner.isPresent()) {
                storedChecking.get().setPrimaryOwner(primaryOwner.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Account Holder with id " + primaryOwnerId + " does not exist.");
            }

            if (checkingDTO.getSecondaryOwnerId() != null) {
                Integer secondaryOwnerId = Integer.parseInt(checkingDTO.getSecondaryOwnerId());
                Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId);
                if (secondaryOwner.isPresent()) {
                    storedChecking.get().setSecondaryOwner(secondaryOwner.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Account Holder with id " + secondaryOwnerId + " does not exist.");
                }
            }

            checkingRepository.save(storedChecking.get());

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checking account with id " + id + " does not exist.");
        }
    }

    public void updateStatus(Integer id, StatusDTO statusDTO) {
        Optional<Checking> storedChecking = checkingRepository.findById(id);

        if (storedChecking.isPresent()) {
            try {
                storedChecking.get().setStatus(Status.valueOf(statusDTO.getStatus().toUpperCase()));
                checkingRepository.save(storedChecking.get());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status value not valid. Only values " + Arrays.toString(Status.values()) + " accepted.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checking account with id " + id + " does not exist.");
        }
    }

    public void updateBalance(Integer id, BalanceDTO balanceDTO) {
        Optional<Checking> storedChecking = checkingRepository.findById(id);

        if (storedChecking.isPresent()) {
            try {
                storedChecking.get().setBalance(new Money(new BigDecimal(balanceDTO.getBalance())));
                checkingRepository.save(storedChecking.get());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance value not valid. Only digits allowed for Balance.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checking account with id " + id + " does not exist.");
        }
    }

    public void delete(Integer id) {
        Optional<Checking> storedChecking = checkingRepository.findById(id);

        if (storedChecking.isPresent()) {
            checkingRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checking account with id " + id + " does not exist.");
        }
    }
}

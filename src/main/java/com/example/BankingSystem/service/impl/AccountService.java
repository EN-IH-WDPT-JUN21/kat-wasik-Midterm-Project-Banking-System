package com.example.BankingSystem.service.impl;

import com.example.BankingSystem.controller.dto.AccountDTO;
import com.example.BankingSystem.controller.dto.BalanceDTO;
import com.example.BankingSystem.controller.dto.StatusDTO;
import com.example.BankingSystem.controller.util.PasswordUtil;
import com.example.BankingSystem.enums.Status;
import com.example.BankingSystem.model.*;
import com.example.BankingSystem.repository.AccountHolderRepository;
import com.example.BankingSystem.repository.AccountRepository;
import com.example.BankingSystem.repository.CheckingAccountRepository;
import com.example.BankingSystem.repository.StudentCheckingAccountRepository;
import com.example.BankingSystem.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public Account store(AccountDTO accountDTO) {
        Account newAccount;

        Integer primaryOwnerId = Integer.parseInt(accountDTO.getPrimaryOwnerId());
        Optional<AccountHolder> primaryOwner = accountHolderRepository.findById(primaryOwnerId);
        if (primaryOwner.isPresent()) {
            LocalDate birthDate = primaryOwner.get().getDateOfBirth();
            LocalDate currentDate = LocalDate.now();
            Integer ownerAge = Period.between(birthDate, currentDate).getYears();
            if (ownerAge < 24) {
                newAccount = new StudentCheckingAccount();
            } else {
                newAccount = new CheckingAccount();
            }

            newAccount.setPrimaryOwner(primaryOwner.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Account Holder with id " + primaryOwnerId + " does not exist.");
        }

        newAccount.setBalance(new Money(new BigDecimal(accountDTO.getBalance())));
        newAccount.setSecretKey(PasswordUtil.encryptedPassword(accountDTO.getSecretKey()));

        if (accountDTO.getSecondaryOwnerId() != null) {
            Integer secondaryOwnerId = Integer.parseInt(accountDTO.getSecondaryOwnerId());
            Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId);
            if (secondaryOwner.isPresent()) {
                newAccount.setSecondaryOwner(secondaryOwner.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Account Holder with id " + secondaryOwnerId + " does not exist.");
            }
        }

        if (!accountRepository.findAll().contains(newAccount)) {
            return accountRepository.save(newAccount);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account already exists in the system.");
        }
    }

    public Money getBalance(Integer id) {
        Optional<Account> storedAccount = accountRepository.findById(id);

        if (storedAccount.isPresent()) {
            return storedAccount.get().getBalance();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account with id " + id + " does not exist.");
        }
    }


    public void update(Integer id, AccountDTO accountDTO) {
        Optional<Account> storedAccount = accountRepository.findById(id);

        if (storedAccount.isPresent()) {
            storedAccount.get().setBalance(new Money(new BigDecimal(accountDTO.getBalance())));
            storedAccount.get().setSecretKey(accountDTO.getSecretKey());

            Integer primaryOwnerId = Integer.parseInt(accountDTO.getPrimaryOwnerId());
            Optional<AccountHolder> primaryOwner = accountHolderRepository.findById(primaryOwnerId);
            if (primaryOwner.isPresent()) {
                storedAccount.get().setPrimaryOwner(primaryOwner.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Account Holder with id " + primaryOwnerId + " does not exist.");
            }

            if (accountDTO.getSecondaryOwnerId() != null) {
                Integer secondaryOwnerId = Integer.parseInt(accountDTO.getSecondaryOwnerId());
                Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId);
                if (secondaryOwner.isPresent()) {
                    storedAccount.get().setSecondaryOwner(secondaryOwner.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Account Holder with id " + secondaryOwnerId + " does not exist.");
                }
            }

            accountRepository.save(storedAccount.get());

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account account with id " + id + " does not exist.");
        }
    }

    public void updateStatus(Integer id, StatusDTO statusDTO) {
        Optional<Account> storedAccount = accountRepository.findById(id);

        if (storedAccount.isPresent()) {
            try {
                storedAccount.get().setStatus(Status.valueOf(statusDTO.getStatus().toUpperCase()));
                accountRepository.save(storedAccount.get());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status value not valid. Only values " + Arrays.toString(Status.values()) + " accepted.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account account with id " + id + " does not exist.");
        }
    }

    public void updateBalance(Integer id, BalanceDTO balanceDTO) {
        Optional<Account> storedAccount = accountRepository.findById(id);

        if (storedAccount.isPresent()) {
            try {
                storedAccount.get().setBalance(new Money(new BigDecimal(balanceDTO.getBalance())));
                accountRepository.save(storedAccount.get());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance value not valid. Only digits allowed for Balance.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account account with id " + id + " does not exist.");
        }
    }

    public void delete(Integer id) {
        Optional<Account> storedAccount = accountRepository.findById(id);

        if (storedAccount.isPresent()) {
            accountRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account account with id " + id + " does not exist.");
        }
    }
}

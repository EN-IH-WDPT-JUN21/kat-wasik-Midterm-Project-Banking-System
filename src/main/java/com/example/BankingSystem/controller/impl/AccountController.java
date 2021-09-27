package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.AccountDTO;
import com.example.BankingSystem.controller.dto.BalanceDTO;
import com.example.BankingSystem.controller.dto.SavingsAccountDTO;
import com.example.BankingSystem.controller.dto.StatusDTO;
import com.example.BankingSystem.controller.interfaces.IAccountController;
import com.example.BankingSystem.model.Account;
import com.example.BankingSystem.model.Money;
import com.example.BankingSystem.repository.AccountRepository;
import com.example.BankingSystem.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController implements IAccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    IAccountService accountService;

    // CREATE
    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewAccount(@RequestBody @Valid AccountDTO accountDTO) {
        return accountService.store(accountDTO);
    }

    @PostMapping("/account/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewSavingsAccount(@RequestBody @Valid SavingsAccountDTO savingsAccountDTO) {
        return accountService.storeSavingsAccount(savingsAccountDTO);
    }

    // READ
    @GetMapping("/account")
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @GetMapping("/account/{id}")
    public Account getById(@PathVariable Integer id, Authentication authentication) {
        String username = authentication.getName();

        return accountService.getById(id, username);
    }

    @GetMapping("/account/{id}/balance")
    public Money getBalance(@PathVariable Integer id, Authentication authentication) {
        String username = authentication.getName();

        return accountService.getBalance(id, username);
    }

    // UPDATE
    @PutMapping("/account/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid AccountDTO accountDTO) {
        accountService.update(id, accountDTO);
    }

    @PutMapping("/account/savings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid SavingsAccountDTO savingsAccountDTO) {
        accountService.updateSavingsAccount(id, savingsAccountDTO);
    }

    @PatchMapping("/account/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody @Valid StatusDTO statusDTO) {
        accountService.updateStatus(id, statusDTO);
    }

    @PatchMapping("/account/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable Integer id, @RequestBody @Valid BalanceDTO balanceDTO) {
        accountService.updateBalance(id, balanceDTO);
    }

    // DELETE
    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable Integer id) {
        accountService.delete(id);
    }
}

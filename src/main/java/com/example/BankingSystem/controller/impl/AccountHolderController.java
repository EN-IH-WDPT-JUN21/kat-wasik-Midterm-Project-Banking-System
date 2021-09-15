package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.AccountHolderDTO;
import com.example.BankingSystem.controller.interfaces.IAccountHolderController;
import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.repository.AccountHolderRepository;
import com.example.BankingSystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/accountholder")
    public List<AccountHolder> getAll() {
        return accountHolderRepository.findAll();
    }

    @GetMapping("/accountholder/{id}")
    public AccountHolder getById(@PathVariable Integer id) {
        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findById(id);

        return accountHolderOptional.isPresent() ? accountHolderOptional.get() : null;
    }

    // UPDATE
    @PutMapping("/accountholder/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        accountHolderService.update(id, accountHolderDTO);
    }

    // DELETE
    @DeleteMapping("/accountholder/{id}")
    public void delete(@PathVariable Integer id) {
        accountHolderRepository.deleteById(id);
    }
}

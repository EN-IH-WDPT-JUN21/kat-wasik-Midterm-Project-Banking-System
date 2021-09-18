package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.AccountHolderDTO;
import com.example.BankingSystem.controller.interfaces.IAccountHolderController;
import com.example.BankingSystem.model.User;
import com.example.BankingSystem.repository.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    IAccountHolderService accountHolderService;

    // CREATE
    @PostMapping("/accountholder")
    @ResponseStatus(HttpStatus.CREATED)
    public User addNewAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        return accountHolderService.store(accountHolderDTO);
    }

//    // READ
//    @GetMapping("/accountholder")
//    public List<AccountHolder> getAll() {
//        return accountHolderRepository.findAll();
//    }
//
//    @GetMapping("/accountholder/{id}")
//    public AccountHolder getById(@PathVariable Integer id) {
//        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findById(id);
//
//        return accountHolderOptional.isPresent() ? accountHolderOptional.get() : null;
//    }
//
//    // UPDATE
//    @PutMapping("/accountholder/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void update(@PathVariable Integer id, @RequestBody @Valid AccountHolderDTO accountHolderDTO) {
//        accountHolderService.update(id, accountHolderDTO);
//    }
//
//    // DELETE
//    @DeleteMapping("/accountholder/{id}")
//    public void delete(@PathVariable Integer id) {
//        accountHolderService.delete(id);
//    }
}

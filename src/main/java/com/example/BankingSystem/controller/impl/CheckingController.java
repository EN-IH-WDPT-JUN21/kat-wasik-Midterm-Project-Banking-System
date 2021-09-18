//package com.example.BankingSystem.controller.impl;
//
//import com.example.BankingSystem.controller.dto.BalanceDTO;
//import com.example.BankingSystem.controller.dto.CheckingDTO;
//import com.example.BankingSystem.controller.dto.StatusDTO;
//import com.example.BankingSystem.controller.interfaces.ICheckingController;
//import com.example.BankingSystem.model.Checking;
//import com.example.BankingSystem.model.Money;
//import com.example.BankingSystem.repository.CheckingRepository;
//import com.example.BankingSystem.service.interfaces.ICheckingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//public class CheckingController implements ICheckingController {
//    @Autowired
//    CheckingRepository checkingRepository;
//
//    @Autowired
//    ICheckingService checkingService;
//
//    // CREATE
//    @PostMapping("/checking")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Checking addNewChecking(@RequestBody @Valid CheckingDTO checkingDTO) {
//        return checkingService.store(checkingDTO);
//    }
//
//    // READ
//    @GetMapping("/checking")
//    public List<Checking> getAll() {
//        return checkingRepository.findAll();
//    }
//
//    @GetMapping("/checking/{id}")
//    public Checking getById(@PathVariable Integer id) {
//        Optional<Checking> checkingOptional = checkingRepository.findById(id);
//
//        return checkingOptional.isPresent() ? checkingOptional.get() : null;
//    }
//
//    @GetMapping("/checking/{id}/balance")
//    public Money getBalance(@PathVariable Integer id) {
//        return checkingService.getBalance(id);
//    }
//
//    // UPDATE
//    @PutMapping("/checking/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void update(@PathVariable Integer id, @RequestBody @Valid CheckingDTO checkingDTO) {
//        checkingService.update(id, checkingDTO);
//    }
//
//    @PatchMapping("/checking/{id}/status")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void updateStatus(@PathVariable Integer id, @RequestBody @Valid StatusDTO statusDTO) {
//        checkingService.updateStatus(id, statusDTO);
//    }
//
//    @PatchMapping("/checking/{id}/balance")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void updateBalance(@PathVariable Integer id, @RequestBody @Valid BalanceDTO balanceDTO) {
//        checkingService.updateBalance(id, balanceDTO);
//    }
//
//    // DELETE
//    @DeleteMapping("/checking/{id}")
//    public void delete(@PathVariable Integer id) {
//        checkingService.delete(id);
//    }
//}

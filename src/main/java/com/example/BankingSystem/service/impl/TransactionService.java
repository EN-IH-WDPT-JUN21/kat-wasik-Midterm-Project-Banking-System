package com.example.BankingSystem.service.impl;

import com.example.BankingSystem.controller.dto.TransactionDTO;
import com.example.BankingSystem.enums.Status;
import com.example.BankingSystem.model.*;
import com.example.BankingSystem.repository.AccountRepository;
import com.example.BankingSystem.repository.TransactionRepository;
import com.example.BankingSystem.repository.UserRepository;
import com.example.BankingSystem.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    // CREATE
    public Transaction store(TransactionDTO transactionDTO, String username) {
        User user = userRepository.findByUsername(username).get();
        Optional<Account> senderAccountOptional = accountRepository.findById(Integer.parseInt(transactionDTO.getSenderAccountId()));
        Optional<Account> receiverAccountOptional = accountRepository.findById(Integer.parseInt(transactionDTO.getReceiverAccountId()));
        String receiverAccountOwnerName = transactionDTO.getReceiverAccountOwnerName();
        Money amount = new Money(new BigDecimal(transactionDTO.getAmount()));

        if (senderAccountOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account with id " + transactionDTO.getSenderAccountId() + " does not exist.");
        }

        if (receiverAccountOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account with id " + transactionDTO.getReceiverAccountId() + " does not exist.");
        }

        Account senderAccount = senderAccountOptional.get();
        Account receiverAccount = receiverAccountOptional.get();

        if (!user.isOwner(senderAccount)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You have no permission to send money from this account (you are not an owner).");
        }

        if (!receiverAccountOwnerName.equals(receiverAccount.getPrimaryOwner().getName())
                && !receiverAccountOwnerName.equals(receiverAccount.getSecondaryOwner().getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receiver account owner name incorrect.");
        }

        if (senderAccount.getStatus().equals(Status.FROZEN)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your account is frozen.");
        }

        if (senderAccount.getMonthlyMaintenanceFee() != null) {
            senderAccount.applyMonthlyMaintenanceFee();
        }

        if (senderAccount.getInterestRate() != null) {
            senderAccount.addInterestRate();
        }

        accountRepository.save(senderAccount);

        Transaction newTransaction = new Transaction();
        newTransaction.setSenderAccount(senderAccount);
        newTransaction.setReceiverAccount(receiverAccount);
        newTransaction.setAmount(amount);

        if (amount.getAmount().compareTo(senderAccount.getBalance().getAmount()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        senderAccount.decreaseBalance(amount);
        receiverAccount.increaseBalance(amount);

        return transactionRepository.save(newTransaction);
    }

    public Transaction getById(Integer id, String username) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);

        if(!transactionOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction with this id " + id + " does not exist.");
        }

        Transaction transaction = transactionOptional.get();

        Account senderAccount = transaction.getSenderAccount();
        Account receiverAccount = transaction.getReceiverAccount();
        User user = userRepository.findByUsername(username).get();

        if (user.isAdmin() || user.isOwner(senderAccount) || user.isOwner(receiverAccount)) {
            return transaction;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to get this transaction.");
        }

    }
}

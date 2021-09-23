package com.example.BankingSystem.service.impl;

import com.example.BankingSystem.controller.dto.TransactionDTO;
import com.example.BankingSystem.enums.Status;
import com.example.BankingSystem.model.Account;
import com.example.BankingSystem.model.Money;
import com.example.BankingSystem.model.Transaction;
import com.example.BankingSystem.model.User;
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
        Optional<Account> senderAccount = accountRepository.findById(Integer.parseInt(transactionDTO.getSenderAccountId()));
        Optional<Account> receiverAccount = accountRepository.findById(Integer.parseInt(transactionDTO.getReceiverAccountId()));
        String receiverAccountOwnerName = transactionDTO.getReceiverAccountOwnerName();
        Money amount = new Money(new BigDecimal(transactionDTO.getAmount()));

        if (senderAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account with id " + transactionDTO.getSenderAccountId() + " does not exist.");
        }

        if (!user.isOwner(senderAccount.get())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You have no permission to send money from this account (you are not an owner).");
        }

        if (receiverAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account with id " + transactionDTO.getReceiverAccountId() + " does not exist.");
        }

        if (!receiverAccountOwnerName.equals(receiverAccount.get().getPrimaryOwner().getName())
                && !receiverAccountOwnerName.equals(receiverAccount.get().getSecondaryOwner().getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receiver account owner name incorrect.");
        }

        if (senderAccount.get().getStatus().equals(Status.FROZEN)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your account is frozen.");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setSenderAccount(senderAccount.get());
        newTransaction.setReceiverAccount(receiverAccount.get());
        newTransaction.setAmount(amount);

        if (amount.getAmount().compareTo(senderAccount.get().getBalance().getAmount()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        senderAccount.get().decreaseBalance(amount);
        receiverAccount.get().increaseBalance(amount);

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

package com.example.BankingSystem.service.impl;

import com.example.BankingSystem.controller.dto.TransactionDTO;
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

    public Transaction store(TransactionDTO transactionDTO, String username) {
        User user = userRepository.findByUsername(username).get();
        Optional<Account> senderAccount = accountRepository.findById(Integer.parseInt(transactionDTO.getSenderAccountId()));
        Optional<Account> receiverAccount = accountRepository.findById(Integer.parseInt(transactionDTO.getReceiverAccountId()));
        String receiverAccountOwnerName = transactionDTO.getReceiverAccountOwnerName();
        Money amount = new Money(new BigDecimal(transactionDTO.getAmount()));

        Transaction newTransaction = new Transaction();

        if (senderAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account with id " + transactionDTO.getSenderAccountId() + " does not exist.");
        }
        if (!user.isOwner(senderAccount.get())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have no permission to send money from this account (you are not an owner).");
        }

        newTransaction.setSenderAccount(senderAccount.get());

        if (receiverAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account with id " + transactionDTO.getReceiverAccountId() + " does not exist.");
        }
        if (!receiverAccountOwnerName.equals(receiverAccount.get().getPrimaryOwner().getName())
                && !receiverAccountOwnerName.equals(receiverAccount.get().getSecondaryOwner().getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receiver account owner name incorrect.");
        }

        newTransaction.setReceiverAccount(receiverAccount.get());
        newTransaction.setAmount(amount);

        if (amount.getAmount().compareTo(senderAccount.get().getBalance().getAmount()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        senderAccount.get().setBalance(new Money(senderAccount.get().getBalance().decreaseAmount(amount)));
        receiverAccount.get().setBalance(new Money(receiverAccount.get().getBalance().increaseAmount(amount.getAmount())));

        return transactionRepository.save(newTransaction);
    }
}

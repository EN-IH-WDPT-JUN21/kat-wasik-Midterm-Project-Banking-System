package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.util.PasswordUtil;
import com.example.BankingSystem.enums.RoleName;
import com.example.BankingSystem.model.Account;
import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.model.Address;
import com.example.BankingSystem.model.Role;
import com.example.BankingSystem.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TransactionRepository transactionRepository;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Address address1;
    Address address2;
    AccountHolder accountHolder1;
    AccountHolder accountHolder2;
    Account account1;
    Account account2;

    Role accountHolderRole = new Role(RoleName.ACCOUNTHOLDER);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        address1 = addressRepository.save(new Address("Ct. Villena 121", "Paredes de Nava", " 34300", "Spain"));
        address2 = addressRepository.save(new Address("3715 Beechwood Drive", "Laurel", "20707", "United States"));

        accountHolder1 = accountHolderRepository.save(new AccountHolder("Mary Poppins", "accountholder1", PasswordUtil.encryptedPassword("!voryRed55"), accountHolderRole, LocalDate.of(1961, 9, 17), address1));
        accountHolder2 = accountHolderRepository.save(new AccountHolder("John Smith", "accountholder2", PasswordUtil.encryptedPassword("w@cky0wl98"), accountHolderRole, LocalDate.of(2005, 9, 23), address1, address2));
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void addNewTransaction_Valid_Created() throws Exception {

    }

    @Test
    void addNewTransaction_SenderAccountDoesNotExist_BadRequest() throws Exception {

    }

    @Test
    void addNewTransaction_ReceiverAccountDoesNotExist_BadRequest() throws Exception {

    }

    @Test
    void addNewTransaction_NotTheOwner_BadRequest() throws Exception {

    }

    @Test
    void addNewTransaction_ReceiverAccountOwnerNameIncorrect_BadRequest() throws Exception {

    }

    @Test
    void addNewTransaction_StatusFrozen_BadRequest() throws Exception {

    }

    @Test
    void addNewTransaction_InsufficientFunds_BadRequest() throws Exception {

    }

    @Test
    void getAllTransactions_Valid_Found() throws Exception {

    }

    @Test
    void getById_Valid_Found() throws Exception {

    }

    @Test
    void deleteTransaction_Valid_Deleted() throws Exception {

    }
}
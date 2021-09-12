package com.example.BankingSystem.repository;

import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountHolderRepositoryTest {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AddressRepository addressRepository;

    Address address1;
    Address address2;
    AccountHolder accountHolder1;

    @BeforeEach
    void setUp() {
        address1 = addressRepository.save(new Address("Ctra. Villena 121", "Paredes de Nava", "34300", "Spain"));
        address2 = addressRepository.save(new Address("3715 Beechwood Drive", "Laurel", "20707", "United States"));
        accountHolder1 = accountHolderRepository.save(new AccountHolder("Marisabel Almaraz", LocalDate.of(1971, 12, 12), address1, address1));
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void saveAccountHolder_successful() {
        assertEquals(1, accountHolderRepository.findAll().size());
        AccountHolder accountHolder2 = accountHolderRepository.save(new AccountHolder("Julie Wilson", LocalDate.of(2002, 1, 23), address1, address2));
        assertEquals(2, accountHolderRepository.findAll().size());
    }

    @Test
    void saveAccountHolder_noMailingAddress_successful() {
        assertEquals(1, accountHolderRepository.findAll().size());
        AccountHolder accountHolder2 = accountHolderRepository.save(new AccountHolder("Julie Wilson", LocalDate.of(2002, 1, 23), address1));
        assertEquals(2, accountHolderRepository.findAll().size());
        assertNull(accountHolder2.getMailingAddress());
    }
}
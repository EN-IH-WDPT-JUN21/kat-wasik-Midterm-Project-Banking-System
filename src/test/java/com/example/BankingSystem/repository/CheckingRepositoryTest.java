package com.example.BankingSystem.repository;

import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.model.Address;
import com.example.BankingSystem.model.Checking;
import com.example.BankingSystem.enums.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckingRepositoryTest {
    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AddressRepository addressRepository;

    Address address1;
    Address address2;
    AccountHolder accountHolder1;
    AccountHolder accountHolder2;
    Checking checking1;

    @BeforeEach
    void setUp() {
        address1 = addressRepository.save(new Address("Ctra. Villena 121", "Paredes de Nava", "34300", "Spain"));
        address2 = addressRepository.save(new Address("3715 Beechwood Drive", "Laurel", "20707", "United States"));
        accountHolder1 = accountHolderRepository.save(new AccountHolder("Marisabel Almaraz", LocalDate.of(1971, 12, 12), address1, address1));
        accountHolder2 = accountHolderRepository.save(new AccountHolder("Julie Wilson", LocalDate.of(2002, 1, 23), address1));
        checking1 = checkingRepository.save(new Checking(new BigDecimal("0"), "(almSnow49", accountHolder1, accountHolder2, new BigDecimal("250"), new BigDecimal("40"), new BigDecimal("12"), Status.ACTIVE));
    }

    @AfterEach
    void tearDown() {
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void saveChecking_successful() {
        assertEquals(1, checkingRepository.findAll().size());
        Checking checking2 = checkingRepository.save(new Checking(new BigDecimal("0"), "thinC@ve23", accountHolder2, accountHolder1, new BigDecimal("250"), new BigDecimal("40"), new BigDecimal("12"), Status.ACTIVE));
        assertEquals(2, checkingRepository.findAll().size());
    }

    @Test
    void saveChecking_allDefaults_successful() {
        assertEquals(1, checkingRepository.findAll().size());
        Checking checking3 = checkingRepository.save(new Checking(new BigDecimal("100"), "paleWhi+e33", accountHolder1, Status.ACTIVE));
        assertEquals(2, checkingRepository.findAll().size());
        assertEquals(new BigDecimal("250"), checking3.getMinimumBalance());
        assertEquals(new BigDecimal("40"), checking3.getPenaltyFee());
        assertEquals(new BigDecimal("12"), checking3.getMonthlyMaintenanceFee());
    }
}
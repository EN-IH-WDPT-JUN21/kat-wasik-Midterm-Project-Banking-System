package com.example.BankingSystem.repository;

import com.example.BankingSystem.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressRepositoryTest {
    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        Address address1 = addressRepository.save(new Address("Ctra. Villena 121", "Paredes de Nava", " 34300", "Spain"));
    }

    @AfterEach
    void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    void saveAddress_succesful() {
        assertEquals(1, addressRepository.findAll().size());
        Address address2 = addressRepository.save(new Address("3715 Beechwood Drive", "Laurel", "20707", "United States"));
        assertEquals(2, addressRepository.findAll().size());
    }
}
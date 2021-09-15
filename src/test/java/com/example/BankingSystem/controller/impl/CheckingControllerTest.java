package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.CheckingDTO;
import com.example.BankingSystem.enums.Status;
import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.model.Address;
import com.example.BankingSystem.model.Checking;
import com.example.BankingSystem.model.Money;
import com.example.BankingSystem.repository.AccountHolderRepository;
import com.example.BankingSystem.repository.AddressRepository;
import com.example.BankingSystem.repository.CheckingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CheckingControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AddressRepository addressRepository;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Address address1;
    Address address2;
    AccountHolder accountHolder1;
    AccountHolder accountHolder2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        address1 = addressRepository.save(new Address("Ct. Villena 121", "Paredes de Nava", " 34300", "Spain"));
        address2 = addressRepository.save(new Address("3715 Beechwood Drive", "Laurel", "20707", "United States"));

        accountHolder1 = accountHolderRepository.save(new AccountHolder("John Smith", LocalDate.of(1945, 9, 23), address1));
        accountHolder2 = accountHolderRepository.save(new AccountHolder("Mary Poppins", LocalDate.of(2001, 1, 31), address2));
    }

    @AfterEach
    void tearDown() {
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void addNewChecking_Valid_Created() throws Exception {
        CheckingDTO checkingDTO = new CheckingDTO();
        checkingDTO.setBalance("1000");
        checkingDTO.setSecretKey("b@dRat89");
        checkingDTO.setPrimaryOwnerId("1");
        checkingDTO.setSecondaryOwnerId("2");

        String body = objectMapper.writeValueAsString(checkingDTO);

        MvcResult mvcResult = mockMvc.perform(post("/checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Checking newChecking = checkingRepository.findById(1).get();

        assertEquals(newChecking.getBalance(), new Money(new BigDecimal("1000")));
        assertEquals(newChecking.getSecretKey(), "b@dRat89");
        assertEquals(newChecking.getStatus(), Status.ACTIVE);
        assertEquals(newChecking.getPrimaryOwner(), accountHolderRepository.findById(1).get());
        assertEquals(newChecking.getSecondaryOwner(), accountHolderRepository.findById(2).get());
        assertEquals(newChecking.getMinimumBalance(), new Money(new BigDecimal("250")));
        assertEquals(newChecking.getPenaltyFee(), new Money(new BigDecimal("40")));
        assertEquals(newChecking.getMonthlyMaintenanceFee(), new Money(new BigDecimal("12")));
    }
}
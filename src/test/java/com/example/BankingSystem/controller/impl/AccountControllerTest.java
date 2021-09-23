package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.AccountDTO;
import com.example.BankingSystem.controller.dto.StatusDTO;
import com.example.BankingSystem.controller.util.PasswordUtil;
import com.example.BankingSystem.enums.RoleName;
import com.example.BankingSystem.enums.Status;
import com.example.BankingSystem.model.*;
import com.example.BankingSystem.repository.*;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountControllerTest {
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

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Address address1;
    Address address2;
    AccountHolder accountHolder1;
    AccountHolder accountHolder2;

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
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void addNewCheckingAccount_Valid_Created() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance("1000");
        accountDTO.setSecretKey("b@dRat89");
        accountDTO.setPrimaryOwnerId("2");
        accountDTO.setSecondaryOwnerId("3");

        String body = objectMapper.writeValueAsString(accountDTO);

        MvcResult mvcResult = mockMvc.perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CheckingAccount newAccount = checkingAccountRepository.findById(1).get();

        assertEquals(newAccount.getBalance(), new Money(new BigDecimal("1000")));
//        assertEquals(newAccount.getSecretKey(), "b@dRat89");
        assertEquals(newAccount.getStatus(), Status.ACTIVE);
        assertEquals(newAccount.getPrimaryOwner(), accountHolderRepository.findById(2).get());
        assertEquals(newAccount.getSecondaryOwner(), accountHolderRepository.findById(3).get());
        assertEquals(newAccount.getMinimumBalance(), new Money(new BigDecimal("250")));
        assertEquals(newAccount.getPenaltyFee(), new Money(new BigDecimal("40")));
        assertEquals(newAccount.getMonthlyMaintenanceFee(), new Money(new BigDecimal("12")));
        assertEquals(newAccount.getCreationDate(), LocalDate.now());
    }

    @Test
    void addNewStudentCheckingAccount_Valid_Created() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance("1000");
        accountDTO.setSecretKey("b@dRat89");
        accountDTO.setPrimaryOwnerId("3");
        accountDTO.setSecondaryOwnerId("2");

        String body = objectMapper.writeValueAsString(accountDTO);

        MvcResult mvcResult = mockMvc.perform(post("/account").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        StudentCheckingAccount newAccount = studentCheckingAccountRepository.findById(1).get();

        assertEquals(newAccount.getBalance(), new Money(new BigDecimal("1000")));
//        assertEquals(newAccount.getSecretKey(), "b@dRat89");
        assertEquals(newAccount.getStatus(), Status.ACTIVE);
        assertEquals(newAccount.getPrimaryOwner(), accountHolderRepository.findById(3).get());
        assertEquals(newAccount.getSecondaryOwner(), accountHolderRepository.findById(2).get());
        assertEquals(newAccount.getPenaltyFee(), new Money(new BigDecimal("40")));
        assertEquals(newAccount.getCreationDate(), LocalDate.now());
    }

//    @Test
//    void addNewAccount_NoSecondaryOwner_Created() throws Exception {
//
//    }
//
//    @Test
//    void addNewAccount_PrimaryOwnerDoesNotExist_BadRequest() throws Exception {
//
//    }
//
//    @Test
//    void addNewAccount_SecondaryOwnerDoestNotExist_BadRequest() throws Exception {
//
//    }
//
//    @Test
//    void getById_Valid_Found() throws Exception {
//
//    }
//
//    @Test
//    void getAll_Valid_Found() throws Exception {
//
//    }

    @Test
    void updateAccount_Valid_Updated() throws Exception {
        CheckingAccount existingAccount = new CheckingAccount();
        existingAccount.setBalance(new Money(new BigDecimal("200")));
        existingAccount.setSecretKey("jollyW@x16");
        existingAccount.setPrimaryOwner(accountHolderRepository.findById(3).get());
        existingAccount.setSecondaryOwner(accountHolderRepository.findById(2).get());
        checkingAccountRepository.save(existingAccount);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance("1000");
        accountDTO.setSecretKey("b@dRat89");
        accountDTO.setPrimaryOwnerId("2");
        accountDTO.setSecondaryOwnerId("3");

        String body = objectMapper.writeValueAsString(accountDTO);

        MvcResult mvcResult = mockMvc.perform(put("/account/1").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        existingAccount = checkingAccountRepository.findById(1).get();

        assertEquals(existingAccount.getBalance(), new Money(new BigDecimal("1000")));
//        assertEquals(existingAccount.getSecretKey(), "b@dRat89");
        assertEquals(existingAccount.getPrimaryOwner(), accountHolderRepository.findById(2).get());
        assertEquals(existingAccount.getSecondaryOwner(), accountHolderRepository.findById(3).get());
    }

//    @Test
//    void updateAccount_DoestNotExist_BadRequest() throws Exception {
//
//    }

    @Test
    void updateStatus_Valid_Updated() throws Exception {
        CheckingAccount existingAccount = new CheckingAccount();
        existingAccount.setBalance(new Money(new BigDecimal("200")));
        existingAccount.setSecretKey("jollyW@x16");
        existingAccount.setPrimaryOwner(accountHolderRepository.findById(3).get());
        existingAccount.setSecondaryOwner(accountHolderRepository.findById(2).get());
        checkingAccountRepository.save(existingAccount);

        StatusDTO statusDTO = new StatusDTO("frozen");
        String body = objectMapper.writeValueAsString(statusDTO);

        MvcResult mvcResult = mockMvc
                .perform(patch("/account/1/status").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        assertEquals(Status.FROZEN, accountRepository.findById(1).get().getStatus());
    }

    @Test
    void updateStatus_InvalidValue_BadRequest() throws Exception {
        CheckingAccount existingAccount = new CheckingAccount();
        existingAccount.setBalance(new Money(new BigDecimal("200")));
        existingAccount.setSecretKey("jollyW@x16");
        existingAccount.setPrimaryOwner(accountHolderRepository.findById(3).get());
        existingAccount.setSecondaryOwner(accountHolderRepository.findById(2).get());
        checkingAccountRepository.save(existingAccount);

        StatusDTO statusDTO = new StatusDTO("invalid value");
        String body = objectMapper.writeValueAsString(statusDTO);

        MvcResult mvcResult = mockMvc
                .perform(patch("/account/1/status").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Status value not valid.")))
                .andReturn();
    }

//    @Test
//    void updateBalance_Valid_Updated() throws Exception {
//
//    }
//
//    @Test
//    void updateBalance_InvalidValue_BadRequest() throws Exception {
//
//    }
//
//    @Test
//    void deleteAccount_Valid_Deleted() throws Exception {
//
//    }
}
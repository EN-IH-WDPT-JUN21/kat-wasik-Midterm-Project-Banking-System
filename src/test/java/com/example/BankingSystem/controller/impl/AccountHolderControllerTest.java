package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.AccountHolderDTO;
import com.example.BankingSystem.model.AccountHolder;
import com.example.BankingSystem.model.Address;
import com.example.BankingSystem.repository.AccountHolderRepository;
import com.example.BankingSystem.repository.AddressRepository;
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

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountHolderControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AddressRepository addressRepository;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Address address1;
    Address address2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        address1 = addressRepository.save(new Address("Ct. Villena 121", "Paredes de Nava", " 34300", "Spain"));
        address2 = addressRepository.save(new Address("3715 Beechwood Drive", "Laurel", "20707", "United States"));
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void addNewAccountHolder_Valid_Created() throws Exception {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setName("John Smith");
        accountHolderDTO.setDateOfBirth("1945-09-23");
        accountHolderDTO.setPrimaryAddressId("1");
        accountHolderDTO.setMailingAddressId("2");

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("John Smith"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1945-09-23"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Ct. Villena 121"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("3715 Beechwood Drive"));
    }

    @Test
    void addNewAccountHolder_NoMailingAddress_Created() throws Exception {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setName("John Smith");
        accountHolderDTO.setDateOfBirth("1945-09-23");
        accountHolderDTO.setPrimaryAddressId("1");

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("John Smith"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1945-09-23"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Ct. Villena 121"));
        assertNull(accountHolderRepository.findByName("John Smith").get().getMailingAddress());
    }

    @Test
    void addNewAccountHolder_WrongDateOfBirthFormat_BadRequest() throws Exception {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setName("John Smith");
        accountHolderDTO.setDateOfBirth("23-09-1945");
        accountHolderDTO.setPrimaryAddressId("1");

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
//                .andExpect(status().reason(containsString("Expected date format: YYYY-MM-DD")))
                .andReturn();
    }

    @Test
    void addNewAcountHolder_DateOfBirthFromTheFuture_BadRequest() throws Exception {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setName("John Smith");
        accountHolderDTO.setDateOfBirth("2055-09-23");
        accountHolderDTO.setPrimaryAddressId("1");

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("The date of birth cannot be in the future.")))
                .andReturn();
    }

    @Test
    void addNewAcountHolder_WrondAddressIdFormat_BadRequest() throws Exception {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setName("John Smith");
        accountHolderDTO.setDateOfBirth("1945-09-23");
        accountHolderDTO.setPrimaryAddressId("a");

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
//                .andExpect(status().reason(containsString("Primary address id must consist of at least one digit and only digits.")))
                .andReturn();
    }

    @Test
    void addNewAccountHolder_PrimaryAddressDoesNotExist_BadRequest() throws Exception {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setName("John Smith");
        accountHolderDTO.setDateOfBirth("1945-09-23");
        accountHolderDTO.setPrimaryAddressId("99");

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("The address with id 99 does not exist.")))
                .andReturn();
    }

    @Test
    void addNewAccountHolder_AccountAlreadyExists_BadRequest() throws Exception {
        AccountHolder existingAccountHolder = new AccountHolder();
        existingAccountHolder.setName("John Smith");
        existingAccountHolder.setDateOfBirth(LocalDate.of(1945, 9, 23));
        existingAccountHolder.setPrimaryAddress(address1);
        accountHolderRepository.save(existingAccountHolder);

        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setName("John Smith");
        accountHolderDTO.setDateOfBirth("1945-09-23");
        accountHolderDTO.setPrimaryAddressId("1");

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("This account holder already exists in the system.")))
                .andReturn();
    }

    @Test
    void updateAccountHolder_DoesNotExist_BadRequest() throws Exception {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setName("John Smith");
        accountHolderDTO.setDateOfBirth("1945-09-23");
        accountHolderDTO.setPrimaryAddressId("1");

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        MvcResult mvcResult = mockMvc.perform(put("/accountholder/99").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Account Holder with id 99 does not exist.")))
                .andReturn();
    }

    @Test
    void updateAccountHolder_Valid_Updated() throws Exception {
        AccountHolder existingAccountHolder = new AccountHolder();
        existingAccountHolder.setName("John Smith");
        existingAccountHolder.setDateOfBirth(LocalDate.of(1945, 9, 23));
        existingAccountHolder.setPrimaryAddress(address1);
        existingAccountHolder.setMailingAddress(address2);
        accountHolderRepository.save(existingAccountHolder);

        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setName("Mary Poppins");
        accountHolderDTO.setDateOfBirth("1995-01-12");
        accountHolderDTO.setPrimaryAddressId("2");
        accountHolderDTO.setMailingAddressId("1");

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        MvcResult mvcResult = mockMvc.perform(put("/accountholder/1").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        AccountHolder updatedAccountHolder = accountHolderRepository.findById(1).get();

        assertEquals("Mary Poppins", updatedAccountHolder.getName());
        assertEquals(LocalDate.of(1995, 1, 12), updatedAccountHolder.getDateOfBirth());
        assertEquals(address2, updatedAccountHolder.getPrimaryAddress());
        assertEquals(address1, updatedAccountHolder.getMailingAddress());
    }
}
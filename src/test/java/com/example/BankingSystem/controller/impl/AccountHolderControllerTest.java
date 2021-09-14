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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
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
        accountHolderRepository.save(new AccountHolder("John Smith", LocalDate.parse("1945-09-23"), address1));

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
}
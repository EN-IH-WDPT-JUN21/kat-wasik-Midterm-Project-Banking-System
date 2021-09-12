package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.model.Address;
import com.example.BankingSystem.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AddressControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    AddressRepository addressRepository;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Address address1;
    Address address2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        address1 = addressRepository.save(new Address("Ctra. Villena 121", "Paredes de Nava", " 34300", "Spain"));
        address2 = addressRepository.save(new Address("3715 Beechwood Drive", "Laurel", "20707", "United States"));
    }

    @AfterEach
    void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    void getAll_Valid_Found() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/address/all"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Villena 121"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("20707"));
    }

    @Test
    void getById_Valid_Found() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("id", Integer.toString(address1.getId()));

        MvcResult mvcResult = mockMvc
                .perform(get("/address").queryParams(params))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Villena 121"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("20707"));
    }

    @Test
    void addNewAddress_Valid_Created() throws Exception {
        Address address3 = new Address("Ansbacher Strasse 50", "Niederweiler", "55491", "Germany");

        String body = objectMapper.writeValueAsString(address3);

        MvcResult mvcResult = mockMvc
                .perform(post("/address").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(addressRepository.exists(Example.of(address3)));
    }
}
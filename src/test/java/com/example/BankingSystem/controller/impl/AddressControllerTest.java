package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.AddressDTO;
import com.example.BankingSystem.model.Address;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        address1 = addressRepository.save(new Address("Ct. Villena 121", "Paredes de Nava", " 34300", "Spain"));
        address2 = addressRepository.save(new Address("3715 Beechwood Drive", "Laurel", "20707", "United States"));
    }

    @AfterEach
    void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    void addNewAddress_Valid_Created() throws Exception {
        AddressDTO addressDTO = new AddressDTO("Ansbacher Strasse 50", "Niederweiler", "55491", "Germany");

        String body = objectMapper.writeValueAsString(addressDTO);

        MvcResult mvcResult = mockMvc
                .perform(post("/address").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Niederweiler"));
    }

    @Test
    void addNewAddress_AlreadyExists_BadRequest() throws Exception {
        AddressDTO addressDTO = new AddressDTO("Ct. Villena 121", "Paredes de Nava", " 34300", "Spain");

        String body = objectMapper.writeValueAsString(addressDTO);

        MvcResult mvcResult = mockMvc
                .perform(post("/address").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("This address already exists in the system.")))
                .andReturn();
    }

    @Test
    void getAll_Valid_Found() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/address"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Villena 121"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("20707"));
    }

    @Test
    void getById_Valid_Found() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/address/" + address1.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Villena 121"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("20707"));
    }

    @Test
    void getById_DoesNotExist_BadRequest() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/address/99"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Address with this id does not exist.")))
                .andReturn();
    }

    @Test
    void updateAddress_Valid_Updated() throws Exception {
        AddressDTO addressDTO = new AddressDTO("Ansbacher Strasse 50", "Niederweiler", "55491", "Germany");

        String body = objectMapper.writeValueAsString(addressDTO);

        Integer id = address1.getId();

        MvcResult mvcResult = mockMvc
                .perform(put("/address/" + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        Address address = addressRepository.findById(id).get();
        assertEquals("Ansbacher Strasse 50", address.getStreet());
        assertEquals("Niederweiler", address.getCity());
        assertEquals("55491", address.getPostalCode());
        assertEquals("Germany", address.getCountry());
    }

    @Test
    void updateAddress_NullValue_BadRequest() throws Exception {
        AddressDTO addressDTO = new AddressDTO(null, "Niederweiler", "55491", "Germany");

        String body = objectMapper.writeValueAsString(addressDTO);

        Integer id = address1.getId();

        MvcResult mvcResult = mockMvc
                .perform(put("/address/" + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateAddress_DoestNotExist_BadRequest() throws Exception {
        AddressDTO addressDTO = new AddressDTO("Ansbacher Strasse 50", "Niederweiler", "55491", "Germany");

        String body = objectMapper.writeValueAsString(addressDTO);

        MvcResult mvcResult = mockMvc
                .perform(put("/address/99").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Address with this id does not exist.")))
                .andReturn();
    }

    @Test
    void deleteAddress_Valid_Deleted() throws Exception {
        Integer id = address1.getId();

        MvcResult mvcResult = mockMvc
                .perform(delete("/address/" + id))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(addressRepository.findAll().contains(address2));
        assertFalse(addressRepository.findAll().contains(address1));
    }
}
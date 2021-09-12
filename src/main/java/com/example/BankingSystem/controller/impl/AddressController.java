package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.interfaces.IAddressController;
import com.example.BankingSystem.model.Address;
import com.example.BankingSystem.repository.AddressRepository;
import com.example.BankingSystem.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AddressController implements IAddressController {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    IAddressService addressService;

    @GetMapping("/address/all")
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @GetMapping("/address")
    public Address getById(@RequestParam Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);

        return optionalAddress.isPresent() ? optionalAddress.get() : null;
    }

    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewAddress(@RequestBody Address address) {
        addressRepository.save(address);
    }
}

package com.example.BankingSystem.controller.impl;

import com.example.BankingSystem.controller.dto.AddressDTO;
import com.example.BankingSystem.controller.interfaces.IAddressController;
import com.example.BankingSystem.model.Address;
import com.example.BankingSystem.repository.AddressRepository;
import com.example.BankingSystem.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AddressController implements IAddressController {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    IAddressService addressService;

    // CREATE
    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public Address addNewAddress(@RequestBody @Valid AddressDTO addressDTO) {
        return addressService.store(addressDTO);
    }

    // READ
    @GetMapping("/address")
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @GetMapping("/address/{id}")
    public Address getById(@PathVariable Integer id) {
        Optional<Address> addressOptional = addressRepository.findById(id);

        return addressOptional.isPresent() ? addressOptional.get() : null;
    }

    // UPDATE
    @PutMapping("/address/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid AddressDTO addressDTO) {
        addressService.update(id, addressDTO);
    }

    // DELETE
    @DeleteMapping("/address/{id}")
    public void delete(@PathVariable Integer id) {
        addressService.delete(id);
    }
}

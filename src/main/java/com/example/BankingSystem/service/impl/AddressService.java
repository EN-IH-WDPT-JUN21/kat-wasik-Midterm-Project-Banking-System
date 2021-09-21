package com.example.BankingSystem.service.impl;

import com.example.BankingSystem.controller.dto.AddressDTO;
import com.example.BankingSystem.model.Address;
import com.example.BankingSystem.repository.AddressRepository;
import com.example.BankingSystem.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {
    @Autowired
    AddressRepository addressRepository;

    public Address store(AddressDTO addressDTO) {
        Address newAddress = new Address(addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getPostalCode(), addressDTO.getCountry());

        if (!addressRepository.findAll().contains(newAddress)) {
            return addressRepository.save(newAddress);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This address already exists in the system.");
        }
    }

    public Address getById(Integer id) {
        Optional<Address> addressOptional = addressRepository.findById(id);

        if (addressOptional.isPresent()) {
            return addressOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address with this id does not exist.");
        }
    }

    public void update(Integer id, AddressDTO addressDTO) {

        Optional<Address> storedAddress = addressRepository.findById(id);

        if (storedAddress.isPresent()) {
            storedAddress.get().setStreet(addressDTO.getStreet());
            storedAddress.get().setCity(addressDTO.getCity());
            storedAddress.get().setPostalCode(addressDTO.getPostalCode());
            storedAddress.get().setCountry(addressDTO.getCountry());
            addressRepository.save(storedAddress.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address with this id does not exist.");
        }
    }

    public void delete(Integer id) {
        Optional<Address> storedAddress = addressRepository.findById(id);

        if (storedAddress.isPresent()) {
            addressRepository.deleteById(id);
        }
    }
}

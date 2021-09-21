package com.example.BankingSystem.service.interfaces;

import com.example.BankingSystem.controller.dto.AddressDTO;
import com.example.BankingSystem.model.Address;

public interface IAddressService {
    public Address store(AddressDTO addressDTO);
    public Address getById(Integer id);
    public void update(Integer id, AddressDTO addressDTO);
    public void delete(Integer id);
}

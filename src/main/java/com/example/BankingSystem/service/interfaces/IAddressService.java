package com.example.BankingSystem.service.interfaces;

import com.example.BankingSystem.controller.dto.AddressDTO;
import com.example.BankingSystem.model.Address;

public interface IAddressService {
    public void update(Integer id, AddressDTO addressDTO);
    public Address store(AddressDTO addressDTO);
    public void delete(Integer id);
}

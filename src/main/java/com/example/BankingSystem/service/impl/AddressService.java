package com.example.BankingSystem.service.impl;

import com.example.BankingSystem.repository.AddressRepository;
import com.example.BankingSystem.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements IAddressService {
    @Autowired
    AddressRepository addressRepository;
}

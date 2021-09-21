package com.example.BankingSystem;

import com.example.BankingSystem.controller.util.PasswordUtil;
import com.example.BankingSystem.enums.RoleName;
import com.example.BankingSystem.model.*;
import com.example.BankingSystem.repository.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class BankingSystemApplication {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(BankingSystemApplication.class, args);
    }

    // Roles are created automatically on application startup
    @Bean
    InitializingBean createRoles() {
        return () -> {

            for (RoleName roleName : RoleName.values()) {
                roleRepository.save(new Role(roleName));
            }
        };
    }

    @Bean
    InitializingBean createAdmin() {
        return () -> {
            adminRepository.save(new Admin("Admin", "admin", "$2a$04$/hrWxYbVvIVJmgzY1QFjU.AAuRUn7rvCCJQTkmYvYVLqlJCS.MmvC", roleRepository.findByName(RoleName.ADMIN).get()));
        // password for admin is "password"
        };
    }

    @Bean
    InitializingBean populateDatabase() {
        return () -> {
            Address address1 = addressRepository.save(new Address("Ct. Villena 121", "Paredes de Nava", " 34300", "Spain"));
            Address address2 = addressRepository.save(new Address("3715 Beechwood Drive", "Laurel", "20707", "United States"));

            AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("John", "john", PasswordUtil.encryptedPassword("password"), roleRepository.findByName(RoleName.ACCOUNTHOLDER).get(), LocalDate.of(1961, 9, 17), address1));
            AccountHolder accountHolder2 = accountHolderRepository.save(new AccountHolder("Mary", "mary", PasswordUtil.encryptedPassword("password"), roleRepository.findByName(RoleName.ACCOUNTHOLDER).get(), LocalDate.of(2005, 9, 17), address2));

            CheckingAccount account1 = accountRepository.save(new CheckingAccount(new Money(new BigDecimal("10000")), "secretkey", accountHolder1));
            StudentCheckingAccount account2 = accountRepository.save(new StudentCheckingAccount(new Money(new BigDecimal("10000")), "secretkey", accountHolder2));
        };
    }
}



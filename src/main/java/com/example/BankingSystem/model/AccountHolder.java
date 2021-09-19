package com.example.BankingSystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountHolder extends User {
    private LocalDate dateOfBirth;

    @ManyToOne
    private Address primaryAddress;

    @ManyToOne
    private Address mailingAddress; // optional

    public AccountHolder(String name, String username, String password, Role role, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(name, username, password, role);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(String name, String username, String password, Role role, LocalDate dateOfBirth, Address primaryAddress) {
        super(name, username, password, role);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }


}

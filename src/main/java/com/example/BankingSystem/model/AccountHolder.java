package com.example.BankingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private LocalDate dateOfBirth;

    @OneToOne
    private Address primaryAddress;

    @OneToOne
    private Address mailingAddress; // optional

    public AccountHolder(String name, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountHolder that = (AccountHolder) o;
        return getName().equals(that.getName()) && getDateOfBirth().equals(that.getDateOfBirth()) && getPrimaryAddress().equals(that.getPrimaryAddress()) && Objects.equals(getMailingAddress(), that.getMailingAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDateOfBirth(), getPrimaryAddress(), getMailingAddress());
    }
}

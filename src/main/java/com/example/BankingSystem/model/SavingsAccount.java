package com.example.BankingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccount extends Account {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimumBalance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimumBalance_amount")),
    })
    private Money minimumBalance = new Money(new BigDecimal("1000")); // default value 1000

    @Column(precision=18, scale=4)
    private BigDecimal interestRate = new BigDecimal("0.0025"); // default value 0.0025

    private LocalDate interestRateLastAdded = LocalDate.now();

    public SavingsAccount(Money balance, String secretKey, AccountHolder primaryOwner) {
        super(balance, secretKey, primaryOwner);
    }
}

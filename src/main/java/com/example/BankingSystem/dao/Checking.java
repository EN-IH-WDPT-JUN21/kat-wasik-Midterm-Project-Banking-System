package com.example.BankingSystem.dao;

import com.example.BankingSystem.enums.Status;
import com.example.BankingSystem.util.Money;
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
@NoArgsConstructor
@AllArgsConstructor
public class Checking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal balance;
    private String secretKey;

    @OneToOne
    private AccountHolder primaryOwner;

    @OneToOne
    private AccountHolder secondaryOwner; // optional

    private BigDecimal minimumBalance = new BigDecimal("250");
    private BigDecimal penaltyFee = new BigDecimal("40");
    private BigDecimal monthlyMaintenanceFee = new BigDecimal("12");
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;
}

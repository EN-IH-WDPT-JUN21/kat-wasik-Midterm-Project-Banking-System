package com.example.BankingSystem.model;

import com.example.BankingSystem.enums.Status;
import lombok.*;

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
    private LocalDate creationDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Status status;

    // No id constructor
    public Checking(BigDecimal balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalance, BigDecimal penaltyFee, BigDecimal monthlyMaintenanceFee, Status status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.minimumBalance = minimumBalance;
        this.penaltyFee = penaltyFee;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.status = status;
    }

    // No id, no secondaryOwner constructor
    public Checking(BigDecimal balance, String secretKey, AccountHolder primaryOwner, BigDecimal minimumBalance, BigDecimal penaltyFee, BigDecimal monthlyMaintenanceFee, Status status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.minimumBalance = minimumBalance;
        this.penaltyFee = penaltyFee;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.status = status;
    }

    // No optional parameters constructor
    public Checking(BigDecimal balance, String secretKey, AccountHolder primaryOwner, Status status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.status = status;
    }
}

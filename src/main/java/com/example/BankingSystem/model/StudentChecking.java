package com.example.BankingSystem.model;

import com.example.BankingSystem.enums.Status;
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
public class StudentChecking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal balance;
    private String secretKey;

    @OneToOne
    private AccountHolder primaryOwner;

    @OneToOne
    private AccountHolder secondaryOwner; // optional

    private BigDecimal penaltyFee = new BigDecimal("40");
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;
}

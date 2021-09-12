package com.example.BankingSystem.model;

import com.example.BankingSystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Savings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal balance;
    private String secretKey;

    @OneToOne
    private AccountHolder primaryOwner;

    @OneToOne
    private AccountHolder secondaryOwner; // optional

    @DecimalMin(value = "100")
    @DecimalMax(value = "1000")
    private BigDecimal minimumBalance = new BigDecimal("2000");

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "currency", column = @Column(name = "penalty_fee_currency")),
            @AttributeOverride( name = "amount", column = @Column(name = "penalty_fee_amount"))
    })
    private Money penaltyFee = new Money(new BigDecimal("40"));
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @DecimalMax(value = "0.5")
    private BigDecimal interestRate = new BigDecimal("0.0025");
}

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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount")),
    })
    private Money balance;
    private String secretKey;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE; // default status for newly opened accounts

    @ManyToOne
    private AccountHolder primaryOwner;

    @ManyToOne
    private AccountHolder secondaryOwner; // optional

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "penaltyFee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penaltyFee_amount")),
    })
    private Money penaltyFee = new Money(new BigDecimal("40")); //default value 40

    private LocalDate creationDate = LocalDate.now();

    public Account(Money balance, String secretKey, AccountHolder primaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
    }

    public Account(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }

    public void increaseBalance(Money amount) {
        setBalance(new Money(balance.increaseAmount(amount)));
    }

    public void decreaseBalance(Money amount) {
        setBalance(new Money(balance.decreaseAmount(amount)));
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", primaryOwner=" + primaryOwner +
                ", secondaryOwner=" + secondaryOwner +
                '}';
    }
}

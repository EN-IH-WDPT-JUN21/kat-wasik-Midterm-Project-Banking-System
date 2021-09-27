package com.example.BankingSystem.model;

import com.example.BankingSystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimumBalance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimumBalance_amount")),
    })
    private Money minimumBalance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount")),
    })
    private Money monthlyMaintenanceFee;

    private LocalDate monthlyFeeLastDeducted;

    @Column(precision=18, scale=4)
    private BigDecimal interestRate;

    private LocalDate interestRateLastAdded;

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

        if (!getMinimumBalance().equals(null)) {
            if (getBalance().getAmount().compareTo(getMinimumBalance().getAmount()) < 0) {
                setBalance(new Money(getBalance().decreaseAmount(getPenaltyFee().getAmount())));
            }
        }
    }

    public void applyMonthlyMaintenanceFee() {
        Period period = Period.between(getMonthlyFeeLastDeducted(), LocalDate.now());
        int monthsPassed = period.getMonths();

        if (monthsPassed > 0) {
            for (int i = monthsPassed; i > 0; i--) {
                this.decreaseBalance(getMonthlyMaintenanceFee());
            }
            setMonthlyFeeLastDeducted(getMonthlyFeeLastDeducted().plusMonths(monthsPassed));
        }
    }

    public void addInterestRate() {
        Period period = Period.between(getInterestRateLastAdded(), LocalDate.now());
        int yearsPassed = period.getYears();

        if (yearsPassed > 0) {
            for (int i = yearsPassed; i > 0; i--) {
                Money interest = new Money(getBalance().getAmount().multiply(getInterestRate()));
                this.increaseBalance(interest);
            }
            setInterestRateLastAdded(getInterestRateLastAdded().plusYears(yearsPassed));
        }
    }
}

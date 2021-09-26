package com.example.BankingSystem.model;

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
public class CheckingAccount extends Account {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimumBalance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimumBalance_amount")),
    })
    private Money minimumBalance = new Money(new BigDecimal("250")); // default value 250

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount")),
    })
    private Money monthlyMaintenanceFee = new Money(new BigDecimal("12")); // default value 12

    private LocalDate monthlyFeeLastAdded = LocalDate.now();

    public CheckingAccount(Money balance, String secretKey, AccountHolder primaryOwner) {
        super(balance, secretKey, primaryOwner);
    }

    public CheckingAccount(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
    }

    @Override
    public void decreaseBalance(Money amount) {
        super.decreaseBalance(amount);

        if (getBalance().getAmount().compareTo(getMinimumBalance().getAmount()) < 0) {
            setBalance(new Money(getBalance().decreaseAmount(getPenaltyFee().getAmount())));
        }
    }

    public void applyMonthlyMaintenanceFee() {
        Period period = Period.between(monthlyFeeLastAdded, LocalDate.now());
        int monthsPassed = period.getMonths();

        if (monthsPassed > 0) {
            for (int i = monthsPassed; i > 0; i--) {
                this.decreaseBalance(monthlyMaintenanceFee);
            }
            setMonthlyFeeLastAdded(monthlyFeeLastAdded.plusMonths(monthsPassed));
        }
    }
}
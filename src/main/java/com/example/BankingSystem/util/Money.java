package com.example.BankingSystem.util;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * source: https://gist.githubusercontent.com/GazzD/a2d8a92ac0b46858070d08bbc4cc4f40/raw/eaf48efd7c191ba1c518f93484e9ce9b6d79e653/Money.java
 */
public class Money {

    private static final Currency USD = Currency.getInstance("USD");
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private final Currency currency;
    private BigDecimal amount;

    /**
     * Class constructor specifying amount, currency, and rounding
     **/

    public Money(BigDecimal amount, Currency currency, RoundingMode rounding) {
        this.currency = currency;
        setAmount(amount.setScale(currency.getDefaultFractionDigits(), rounding));
    }

    /**
     * Class constructor specifying amount, and currency. Uses default RoundingMode HALF_EVEN.
     **/
    public Money(BigDecimal amount, Currency currency) {
        this(amount, currency, DEFAULT_ROUNDING);
    }

    /**
     * Class constructor specifying amount. Uses default RoundingMode HALF_EVEN and default currency USD.
     **/
    public Money(BigDecimal amount) {
        this(amount, USD, DEFAULT_ROUNDING);
    }

    public BigDecimal increaseAmount(Money money) {
        setAmount(this.amount.add(money.amount));
        return this.amount;
    }

    public BigDecimal increaseAmount(BigDecimal addAmount) {
        setAmount(this.amount.add(addAmount));
        return this.amount;
    }

    public BigDecimal decreaseAmount(Money money) {
        setAmount(this.amount.subtract(money.getAmount()));
        return this.amount;
    }

    public BigDecimal decreaseAmount(BigDecimal addAmount) {
        setAmount(this.amount.subtract(addAmount));
        return this.amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    private void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String toString() {
        return getCurrency().getSymbol() + " " + getAmount();
    }
}
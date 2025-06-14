package com.grabbit.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;
import static java.math.RoundingMode.HALF_UP;

public class Money {

    private final BigDecimal amount;
    public Money(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount cannot be null or negative.");
        }
        this.amount = amount.setScale(2, HALF_UP);
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public Money add(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot add null Money.");
        }
        BigDecimal newAmount = this.amount.add(other.getAmount());
        return new Money(newAmount);
    }

    public Money multiply(BigDecimal multiplier) {
        if (multiplier == null || multiplier.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Multiplier must be positive.");
        }
        BigDecimal newAmount = this.amount.multiply(multiplier);
        return new Money(newAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return "₹" + amount;
    }
}

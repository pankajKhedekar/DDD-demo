package com.grabbit.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void constructor_shouldThrowException_whenAmountIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Money(null));
        assertEquals("Money amount cannot be null or negative.", thrown.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenAmountIsNegative() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Money(new BigDecimal("-5.00")));
        assertEquals("Money amount cannot be null or negative.", thrown.getMessage());
    }

    @Test
    void constructor_shouldSetScaleToTwoDecimals_whenValidAmountIsGiven() {
        Money money = new Money(new BigDecimal("19.999"));
        assertEquals(new BigDecimal("20.00"), money.getAmount());
    }

    @Test
    void add_shouldReturnCorrectSum_whenAddingTwoMoneyObjects() {
        Money money1 = new Money(new BigDecimal("10.50"));
        Money money2 = new Money(new BigDecimal("5.25"));

        Money result = money1.add(money2);

        assertEquals(new BigDecimal("15.75"), result.getAmount());
    }

    @Test
    void add_shouldThrowException_whenAddingNullMoney() {
        Money money = new Money(new BigDecimal("10.00"));
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> money.add(null));
        assertEquals("Cannot add null Money.", thrown.getMessage());
    }

    @Test
    void multiply_shouldReturnCorrectAmount_whenMultiplyingMoneyWithPositiveMultiplier() {
        Money money = new Money(new BigDecimal("5.00"));
        Money result = money.multiply(new BigDecimal("2"));

        assertEquals(new BigDecimal("10.00"), result.getAmount());
    }

    @Test
    void multiply_shouldThrowException_whenMultiplierIsNull() {
        Money money = new Money(new BigDecimal("5.00"));
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> money.multiply(null));
        assertEquals("Multiplier must be positive.", thrown.getMessage());
    }

    @Test
    void multiply_shouldThrowException_whenMultiplierIsNegative() {
        Money money = new Money(new BigDecimal("5.00"));
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> money.multiply(new BigDecimal("-2")));
        assertEquals("Multiplier must be positive.", thrown.getMessage());
    }

    @Test
    void equals_shouldReturnTrue_whenMoneyObjectsHaveSameAmount() {
        Money money1 = new Money(new BigDecimal("10.50"));
        Money money2 = new Money(new BigDecimal("10.50"));

        assertTrue(money1.equals(money2));
    }

    @Test
    void equals_shouldReturnFalse_whenMoneyObjectsHaveDifferentAmount() {
        Money money1 = new Money(new BigDecimal("10.50"));
        Money money2 = new Money(new BigDecimal("5.50"));

        assertFalse(money1.equals(money2));
    }

    @Test
    void hashCode_shouldBeConsistentWithEquals() {
        Money money1 = new Money(new BigDecimal("10.50"));
        Money money2 = new Money(new BigDecimal("10.50"));

        assertEquals(money1.hashCode(), money2.hashCode());
    }

    @Test
    void toString_shouldReturnCorrectStringRepresentation() {
        Money money = new Money(new BigDecimal("10.50"));

        assertEquals("₹10.50", money.toString());
    }
}

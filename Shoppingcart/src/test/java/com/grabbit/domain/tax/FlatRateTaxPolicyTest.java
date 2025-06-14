package com.grabbit.domain.tax;

import com.grabbit.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlatRateTaxPolicyTest {

    private FlatRateTaxPolicy taxPolicy;

    @BeforeEach
    void setUp() {
        taxPolicy = new FlatRateTaxPolicy(new BigDecimal("0.10")); // 10% tax rate
    }

    @Test
    void whenTaxIsCalculatedForValidSubtotal_thenItShouldReturnCorrectTaxAmount() {
        Money subtotal = new Money(new BigDecimal("100.00"));
        Money expectedTax = new Money(new BigDecimal("10.00")); // 100 * 0.10 = 10.00

        Money calculatedTax = taxPolicy.calculateTax(subtotal);

        assertEquals(expectedTax, calculatedTax);
    }

    @Test
    void whenTaxIsCalculatedForZeroSubtotal_thenItShouldReturnZeroTax() {
        Money subtotal = new Money(new BigDecimal("0.00"));
        Money expectedTax = new Money(new BigDecimal("0.00"));

        Money calculatedTax = taxPolicy.calculateTax(subtotal);

        assertEquals(expectedTax, calculatedTax);
    }

    @Test
    void whenTaxIsCalculatedWithDifferentRate_thenItShouldReturnCorrectTax() {
        FlatRateTaxPolicy differentTaxPolicy = new FlatRateTaxPolicy(new BigDecimal("0.15")); // 15% tax rate
        Money subtotal = new Money(new BigDecimal("200.00"));
        Money expectedTax = new Money(new BigDecimal("30.00")); // 200 * 0.15 = 30.00

        Money calculatedTax = differentTaxPolicy.calculateTax(subtotal);

        assertEquals(expectedTax, calculatedTax);
    }

    @Test
    void whenTaxIsCalculatedForLargeSubtotal_thenItShouldReturnCorrectTax() {
        Money subtotal = new Money(new BigDecimal("100000.00"));
        Money expectedTax = new Money(new BigDecimal("10000.00")); // 100000 * 0.10 = 10000.00

        Money calculatedTax = taxPolicy.calculateTax(subtotal);

        assertEquals(expectedTax, calculatedTax);
    }
}

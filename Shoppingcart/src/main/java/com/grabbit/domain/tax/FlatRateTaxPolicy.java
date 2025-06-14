package com.grabbit.domain.tax;

import com.grabbit.domain.valueobject.Money;

import java.math.BigDecimal;

public class FlatRateTaxPolicy implements TaxPolicy {

    private final BigDecimal rate;

    public FlatRateTaxPolicy(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public Money calculateTax(Money subtotal) {
        BigDecimal taxAmount = subtotal.getAmount().multiply(rate);
        return new Money(taxAmount);
    }
}

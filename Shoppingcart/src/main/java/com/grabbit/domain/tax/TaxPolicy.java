package com.grabbit.domain.tax;

import com.grabbit.domain.valueobject.Money;

public interface TaxPolicy {

    Money calculateTax(Money subtotal);
}

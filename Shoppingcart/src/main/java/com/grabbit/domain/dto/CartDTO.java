package com.grabbit.domain.dto;

import com.grabbit.domain.valueobject.CartItem;
import com.grabbit.domain.valueobject.Money;

import java.util.List;

public record CartDTO(String cartId, List<CartItem> items, Money subtotal, Money tax, Money total) {

    @Override
    public String toString() {
        return "CartDTO{" +
                "cartId='" + cartId + '\'' +
                ", items=" + items +
                ", subtotal=" + subtotal +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }
}

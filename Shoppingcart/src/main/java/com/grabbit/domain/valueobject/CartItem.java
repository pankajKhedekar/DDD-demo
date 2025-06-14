package com.grabbit.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public class CartItem {

    private final String productId;
    private final int quantity;

    public CartItem(String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    // Only stores productId and quantity, no price here.
    public Money subTotal(Money price) {
        return price.multiply(new BigDecimal(quantity));
    }

    public CartItem withUpdatedQuantity(int newQuantity) {
        return new CartItem(productId, newQuantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity && Objects.equals(productId, cartItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

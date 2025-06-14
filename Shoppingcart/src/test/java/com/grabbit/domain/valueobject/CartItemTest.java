package com.grabbit.domain.valueobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        cartItem = new CartItem("prod1", 5);
    }

    @Test
    void whenCartItemIsCreatedWithValidQuantity_thenItShouldBeInitializedCorrectly() {
        assertEquals("prod1", cartItem.getProductId());
        assertEquals(5, cartItem.getQuantity());
    }

    @Test
    void whenCartItemIsCreatedWithInvalidQuantity_thenItShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CartItem("prod1", 0);
        });

        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    @Test
    void whenSubTotalIsCalculated_thenItShouldReturnCorrectAmount() {
        Money price = new Money(new BigDecimal("10.00"));
        Money expectedSubTotal = new Money(new BigDecimal("50.00"));

        Money subTotal = cartItem.subTotal(price);

        assertEquals(expectedSubTotal, subTotal);
    }

    @Test
    void whenCartItemQuantityIsUpdated_thenItShouldReturnNewCartItem() {
        CartItem updatedCartItem = cartItem.withUpdatedQuantity(10);

        assertEquals(10, updatedCartItem.getQuantity());
        assertNotEquals(cartItem, updatedCartItem);
    }
}

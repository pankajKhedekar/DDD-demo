package com.grabbit.domain.aggregate;

import com.grabbit.domain.valueobject.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart("cart1");
    }

    @Test
    void whenCartIsEmpty_thenItShouldReturnEmptyListOfItems() {
        assertTrue(cart.getItems().isEmpty(), "Cart should be empty initially.");
    }

    @Test
    void whenProductIsAdded_thenItShouldAppearInTheCart() {
        cart.addProduct(new CartItem("prod1", 5));

        CartItem item = cart.getItems().get(0);
        assertEquals("prod1", item.getProductId(), "ProductDTO ID should be 'prod1'.");
        assertEquals(5, item.getQuantity(), "Quantity should be 5.");
    }

    @Test
    void whenExceedingMaxCartSize_thenItShouldThrowException() {
        // Simulate adding MAX_CART_SIZE products
        for (int i = 1; i <= Cart.MAX_CART_SIZE; i++) {
            cart.addProduct(new CartItem("prod1"+i, 5));
        }

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addProduct(new CartItem("prod51", 5));
        });

        assertEquals("Cannot add more than 50 items to the cart.", exception.getMessage());
    }

    @Test
    void whenProductQuantityExceedsMaxAllowed_thenItShouldThrowException() {
        cart.addProduct(new CartItem("prod1", 50));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addProduct(new CartItem("prod1", 1));
        });

        assertEquals("Quantity for product prod1 exceeds limit.", exception.getMessage());
    }

    @Test
    void whenUpdatingProductQuantity_thenItShouldUpdateCorrectly() {
        cart.addProduct(new CartItem("prod1", 5));
        cart.addProduct(new CartItem("prod1", 10));

        CartItem item = cart.getItems().get(0);

        assertEquals(1, cart.getItems().size());
        assertEquals(15, item.getQuantity(), "The total quantity for prod1 should be 15.");
    }

    @Test
    void whenAddingMultipleProducts_thenTheyShouldBeInTheCart() {
        cart.addProduct(new CartItem("prod1", 5));
        cart.addProduct(new CartItem("prod2", 5));
        cart.addProduct(new CartItem("prod3", 5));

        assertEquals(3, cart.getItems().size(), "There should be 3 products in the cart.");
    }

    @Test
    void whenAddingProductWithInvalidQuantity_thenItShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addProduct(new CartItem("prod1", -5));
        });

        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    @Test
    void whenAddingProductAndExceedingMaxQuantity_thenItShouldThrowException() {
        cart.addProduct(new CartItem("prod1", 50));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addProduct(new CartItem("prod1", 1));
        });

        assertEquals("Quantity for product prod1 exceeds limit.", exception.getMessage());
    }
}

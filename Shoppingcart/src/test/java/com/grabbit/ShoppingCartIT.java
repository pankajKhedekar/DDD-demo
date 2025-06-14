package com.grabbit;

import com.grabbit.domain.service.CartService;
import com.grabbit.domain.tax.FlatRateTaxPolicy;
import com.grabbit.domain.valueobject.CartItem;
import com.grabbit.domain.valueobject.Money;
import com.grabbit.infrastructure.client.ProductClient;
import com.grabbit.infrastructure.repository.InMemoryCartRepository;
import com.grabbit.infrastructure.repository.ProductRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartIT {

    @Test
    public void testHappyPath(){
        var cartRepository = new InMemoryCartRepository();
        var productRepository = new ProductRepositoryImpl(new ProductClient());
        var taxPolicy = new FlatRateTaxPolicy(new BigDecimal("0.125"));
        var cartService = new CartService(cartRepository, productRepository, taxPolicy);
        var cartItem1 = new CartItem("cornflakes", 1);
        var cartItem2 = new CartItem("cornflakes", 1);
        var cartItem3 = new CartItem("weetabix", 1);
        var expectedCartItem1 = new CartItem("cornflakes", 2);
        var expectedCartItem2 = new CartItem("weetabix", 1);

        var result = cartService.addProductsToCart("cart1", List.of(cartItem1, cartItem2, cartItem3));

        assertNotNull(result);
        assertTrue(result.items().contains(expectedCartItem1));
        assertTrue(result.items().contains(expectedCartItem2));
        assertEquals(2, result.items().size());
        assertEquals(new Money(BigDecimal.valueOf(15.02)), result.subtotal());
        assertEquals(new Money(BigDecimal.valueOf(1.88)), result.tax());
        assertEquals(new Money(BigDecimal.valueOf(16.90)), result.total());
    }
}

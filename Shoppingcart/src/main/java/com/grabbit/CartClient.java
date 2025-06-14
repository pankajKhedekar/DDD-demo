package com.grabbit;

import com.grabbit.domain.service.CartService;
import com.grabbit.domain.tax.FlatRateTaxPolicy;
import com.grabbit.domain.valueobject.CartItem;
import com.grabbit.infrastructure.client.ProductClient;
import com.grabbit.infrastructure.repository.InMemoryCartRepository;
import com.grabbit.infrastructure.repository.ProductRepositoryImpl;

import java.math.BigDecimal;
import java.util.List;

public class CartClient {

    public static void main(String[] args) {
        var cartRepository = new InMemoryCartRepository();
        var productRepository = new ProductRepositoryImpl(new ProductClient());
        var taxPolicy = new FlatRateTaxPolicy(new BigDecimal("0.125"));
        var cartService = new CartService(cartRepository, productRepository, taxPolicy);

        var cartItem1 = new CartItem("cornflakes", 1);
        var cartItem2 = new CartItem("cornflakes", 1);
        var cartItem3 = new CartItem("weetabix", 1);

        var cart1 = cartService.addProductsToCart("cart1", List.of(cartItem1, cartItem2, cartItem3));

        System.out.println(cart1);
    }
}

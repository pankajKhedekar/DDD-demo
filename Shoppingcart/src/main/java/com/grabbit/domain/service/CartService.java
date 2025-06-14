package com.grabbit.domain.service;

import com.grabbit.domain.aggregate.Cart;
import com.grabbit.domain.dto.CartDTO;
import com.grabbit.domain.repository.CartRepository;
import com.grabbit.domain.repository.ProductRepository;
import com.grabbit.domain.tax.TaxPolicy;
import com.grabbit.domain.valueobject.CartItem;
import com.grabbit.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.List;

public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final TaxPolicy taxPolicy;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, TaxPolicy taxPolicy) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.taxPolicy = taxPolicy;
    }

    public CartDTO addProductsToCart(String cartId, List<CartItem> products) {
        var cart = cartRepository.findById(cartId).orElse(new Cart(cartId));

        for (CartItem cartItem : products) {
            var productId = cartItem.getProductId();
            if (isValidProduct(productId)) {
                cart.addProduct(cartItem);
            } else {
                throw new IllegalArgumentException("Product not found: " + productId);
            }
        }

        cartRepository.save(cart);

        return calculateCartState(cart);
    }

    public CartDTO getCart(String cartId){
        var cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found: " + cartId));
        return calculateCartState(cart);
    }
    private boolean isValidProduct(String productId) {
        return productRepository.fetchProductById(productId).isPresent();
    }

    private CartDTO calculateCartState(Cart cart) {
        Money subtotal = calculateSubtotal(cart);
        Money tax = calculateTax(subtotal);
        Money total = subtotal.add(tax);

        return new CartDTO(cart.getId(), cart.getItems(), subtotal, tax, total);
    }

    private Money calculateSubtotal(Cart cart) {
        var subtotal = new Money(BigDecimal.ZERO);

        for (CartItem item : cart.getItems()) {
            var product = productRepository.fetchProductById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProductId()));
            subtotal = subtotal.add(item.subTotal(product.getPrice()));
        }

        return subtotal;
    }
    private Money calculateTax(Money subtotal) {
        return taxPolicy.calculateTax(subtotal);
    }
}
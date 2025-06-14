package com.grabbit.domain.aggregate;

import com.grabbit.domain.valueobject.CartItem;

import java.util.*;

public class Cart {

    public static final int MAX_CART_SIZE = 50;
    public static final int MAX_ALLOWED_QTY = 50;
    private final String id;
    private final Map<String, CartItem> items;

    public Cart(String id) {
        this.id = id;
        this.items = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items.values().stream().toList();
    }

    public void addProduct(CartItem item) {
        if (items.size() >= MAX_CART_SIZE) {
            throw new IllegalArgumentException("Cannot add more than "+ MAX_CART_SIZE + " items to the cart.");
        }

        items.compute(item.getProductId(), (key, existingItem) -> {
            if (existingItem == null) {
                return item;
            }
            int newQuantity = existingItem.getQuantity() + item.getQuantity();
            if (newQuantity <= MAX_ALLOWED_QTY) {
                return existingItem.withUpdatedQuantity(newQuantity);
            }
            throw new IllegalArgumentException("Quantity for product " + item.getProductId() + " exceeds limit.");
        });
    }
}

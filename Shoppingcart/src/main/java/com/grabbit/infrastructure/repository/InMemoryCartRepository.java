package com.grabbit.infrastructure.repository;

import com.grabbit.domain.aggregate.Cart;
import com.grabbit.domain.repository.CartRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCartRepository implements CartRepository {

    private final Map<String, Cart> cartStore = new HashMap<>();

    @Override
    public Cart save(Cart cart) {
        cartStore.put(cart.getId(), cart);
        return cart;
    }

    @Override
    public Optional<Cart> findById(String id) {
        return Optional.ofNullable(cartStore.get(id));
    }
}

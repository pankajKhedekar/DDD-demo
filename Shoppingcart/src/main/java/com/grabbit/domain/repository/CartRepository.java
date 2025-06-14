package com.grabbit.domain.repository;

import com.grabbit.domain.aggregate.Cart;

import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);
    Optional<Cart> findById(String id);
}

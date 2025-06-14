package com.grabbit.domain.repository;

import com.grabbit.domain.dto.ProductDTO;

import java.util.Optional;

public interface ProductRepository {
    Optional<ProductDTO> fetchProductById(String productId);
}

package com.grabbit.infrastructure.repository;

import com.grabbit.domain.dto.ProductDTO;
import com.grabbit.domain.repository.ProductRepository;
import com.grabbit.infrastructure.client.ProductClient;

import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private final ProductClient productClient;

    public ProductRepositoryImpl(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Override
    public Optional<ProductDTO> fetchProductById(String productId) {
        return productClient.fetchProductById(productId);
    }
}

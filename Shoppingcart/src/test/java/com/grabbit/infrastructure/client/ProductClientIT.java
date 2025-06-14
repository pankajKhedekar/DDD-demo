package com.grabbit.infrastructure.client;

import com.grabbit.domain.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductClientIT {

    private ProductClient productClient;

    @BeforeEach
    public void setUp() {
        productClient = new ProductClient();
    }

    @ParameterizedTest
    @CsvSource({
            "cheerios, true",
            "cornflakes, true",
            "frosties, true",
            "shreddies, true",
            "weetabix, true",
            "invalid-product-id, false",
            "bad-id, false"
    })
    public void testFetchProductById_withDifferentIds(String productId, boolean shouldPresent) {
        Optional<ProductDTO> productDTOOptional = productClient.fetchProductById(productId);

        if (shouldPresent) {
            assertTrue(productDTOOptional.isPresent(), "Product should be present.");
            var productDTO = productDTOOptional.get();
            assertEquals(productId, productDTO.getId(), "Product ID should match.");
            assertNotNull(productDTO.getName(), "Product name should not be null.");
            assertNotNull(productDTO.getPrice(), "Product price should not be null.");
        } else {
            assertFalse(productDTOOptional.isPresent(), "Product should not be present.");
        }
    }
}

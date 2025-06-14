package com.grabbit.infrastructure.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grabbit.domain.dto.ProductDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import static java.lang.String.format;

public class ProductClient {

    private static final String PRODUCT_API_TEMPLATE = "https://equalexperts.github.io/backend-take-home-test-data/%s.json";

    private final ObjectMapper objectMapper;

    public ProductClient() {
        this.objectMapper = new ObjectMapper();
    }

    public Optional<ProductDTO> fetchProductById(String productId) {
        var requestUrl = format(PRODUCT_API_TEMPLATE, productId);

        HttpURLConnection connection = null;
        try {
            var url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Failed to fetch product data, HTTP code: " + responseCode);
            }

            // Read the response from the API
            var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            var responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            var responseBody = responseBuilder.toString();
            var product = objectMapper.readValue(responseBody, ProductDTO.class);

            return Optional.of(product.withId(productId));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

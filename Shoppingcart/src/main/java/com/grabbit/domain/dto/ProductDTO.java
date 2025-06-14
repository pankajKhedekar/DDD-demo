package com.grabbit.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grabbit.domain.valueobject.Money;

import java.io.Serializable;
import java.util.Objects;

public class ProductDTO implements Serializable {

    private String id;
    private final String name;
    private final Money price;

    // Constructor used by Jackson for deserialization
    @JsonCreator
    public ProductDTO(@JsonProperty("id") String id,
                      @JsonProperty("title") String name,
                      @JsonProperty("price") Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductDTO withId(String id){
        return new ProductDTO(id, name, price);
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return id.equals(that.id) && name.equals(that.name) && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

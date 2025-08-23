package com.codewithmosh.store.products;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Byte category_id;
}

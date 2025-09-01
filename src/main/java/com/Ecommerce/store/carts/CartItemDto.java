package com.Ecommerce.store.carts;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private CartProductDto product;
    private int quantity;
    private BigDecimal totalprice=BigDecimal.ZERO;
}

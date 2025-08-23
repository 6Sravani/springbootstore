package com.codewithmosh.store.carts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalprice",expression = "java(cart.getTotal())")
    CartDto toDto(Cart cart);
    @Mapping(target = "totalprice" , expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}

package com.Ecommerce.store.carts;

import com.Ecommerce.store.products.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name="Carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    @Operation(summary = "Creates a cart")
    public ResponseEntity<CartDto> createCart() {
        var cartDto=cartService.CreateCart();
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @Parameter(description = "card id")
            @PathVariable(name = "id") UUID cartId,
            @RequestBody AddItemsToCartRequest request) {
        var cartItemsDto=cartService.addCartItem(cartId,request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemsDto);
    }

    @GetMapping("/{id}")
    public CartDto getCartById(@PathVariable(name = "id") UUID cartId) {
        return cartService.getCartById(cartId);
    }

    @PutMapping("/{id}/items/{productId}")
    public CartItemDto updateCart(
            @PathVariable(name = "id") UUID cardId,
            @Valid @RequestBody UpdateCartIteamRequest request,
            @PathVariable(name = "productId") Long productId
    ){
        return cartService.updateCart(cardId,productId,request.getQuantity());
    }

    @DeleteMapping("/{id}/items/{productId}")
    public ResponseEntity<?> deleteItemFromCart(
            @PathVariable(name = "id") UUID cartId,
            @PathVariable(name = "productId") Long productId
    ){
        cartService.deleteItemFromCar(cartId,productId);
        return ResponseEntity.noContent().build();

    }
    @DeleteMapping("/{id}/items")
    public ResponseEntity<?> clearCart(@PathVariable(name = "id") UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({CartNotFoundException.class})
    public ResponseEntity<Map<String,String>> handleCartNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("message","Cart not found")
        );
    }
    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<Map<String,String>> handleProductNotFoundException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("message","Product not found in the cart")
        );
    }
}

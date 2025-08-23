package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", updatable = false,insertable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "card",orphanRemoval = true, cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public CartItem getItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    public void addItem(CartItem cartItem, Product product) {
        if(cartItem!=null) {
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else{
            CartItem cartItem1=new CartItem();
            cartItem1.setProduct(product);
            cartItem1.setQuantity(1);
            cartItem1.setCard(this);
            items.add(cartItem1);
        }
    }
    public void removeItem(Long productId) {
        var cartItem = getItem(productId);
        if(cartItem!=null) {
            items.remove(cartItem);
            cartItem.setCard(null);
        }
    }
    public  void clearItems() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}
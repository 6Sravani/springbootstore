package com.Ecommerce.store.payments;

import lombok.Data;

@Data
public class CheckOutResponse {
    private Long orderId;
    private String checkoutUrl;

    public CheckOutResponse(Long id,String checkoutUrl) {
        this.orderId = id;
        this.checkoutUrl = checkoutUrl;
    }
}

package com.Ecommerce.store.payments;

import com.Ecommerce.store.orders.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private  Long orderId;
    private PaymentStatus paymentStatus;
}

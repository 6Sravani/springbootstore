package com.Ecommerce.store.payments;

import com.Ecommerce.store.orders.Order;
import com.Ecommerce.store.carts.CartEmptyException;
import com.Ecommerce.store.carts.CartNotFoundException;
import com.Ecommerce.store.carts.CartRepository;
import com.Ecommerce.store.orders.OrderRepository;
import com.Ecommerce.store.auth.AuthService;
import com.Ecommerce.store.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheckOutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckOutResponse checkOut(CheckOutRequest request){
        var cart=cartRepository.findById(request.getCartId()).orElse(null);
        if(cart==null){
            throw new CartNotFoundException();
        }
        if(cart.isEmpty()){
            throw new CartEmptyException();
        }

        var order= Order.fromCart(cart,authService.getCurrentUser());

        orderRepository.save(order);

        try{
            var session=paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cart.getId());

            return new CheckOutResponse(order.getId(), session.getCheckoutUrl());
        }catch(PaymentException ex){
            orderRepository.delete(order);
            throw ex;
        }
    }
    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                    var order=orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                });
    }
}

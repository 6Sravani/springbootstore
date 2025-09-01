package com.Ecommerce.store.payments;

import com.Ecommerce.store.dtos.ErrorDto;
import com.Ecommerce.store.carts.CartEmptyException;
import com.Ecommerce.store.carts.CartNotFoundException;
import com.Ecommerce.store.orders.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckOutController {
    private  final CheckOutService checkOutService;
    private final OrderRepository orderRepository;



    @PostMapping
    public CheckOutResponse checkout(
            @Valid @RequestBody CheckOutRequest request
    ){
        return checkOutService.checkOut(request);
    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String,String> headers,
            @RequestBody String payload
    ){
        checkOutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}

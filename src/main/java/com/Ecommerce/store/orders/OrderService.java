package com.Ecommerce.store.orders;

import com.Ecommerce.store.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        var user=authService.getCurrentUser();
        var orders=orderRepository.getOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toDTo).toList();
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);
        var user=authService.getCurrentUser();
        if (!order.isPlacedBy(user)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
        return orderMapper.toDTo(order);
    }
}

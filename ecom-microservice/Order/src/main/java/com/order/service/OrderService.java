package com.order.service;

import java.util.List;

import com.order.dto.OrderDto;

public interface OrderService {
	OrderDto placeOrder(Long userId, String shippingAddress, String paymentMethod);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getOrdersByUserId(Long userId);
    List<OrderDto> getAllOrders();
    OrderDto updateOrderStatus(Long orderId, String status);

}

package com.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.dto.CartDto;
import com.order.dto.OrderDto;
import com.order.dto.OrderItemDto;
import com.order.entity.Order;
import com.order.entity.OrderItem;
import com.order.exception.OrderNotFoundException;
import com.order.exception.ResourceNotFoundException;
import com.order.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderDto placeOrder(Long userId, String shippingAddress, String paymentMethod) {
		// Get user's cart
		CartDto cartDto = cartService.getCartByUserId(userId);

		if (cartDto.getItems() == null || cartDto.getItems().isEmpty()) {
			throw new ResourceNotFoundException("Cannot place order with empty cart");
		}

		// Create new order
		Order order = new Order();
		order.setUserId(userId);
		order.setOrderDate(LocalDateTime.now());
		order.setStatus("PENDING");
		order.setTotalAmount(cartDto.getTotalPrice());
		order.setShippingAddress(shippingAddress);
		order.setPaymentMethod(paymentMethod);

		// Convert cart items to order items
		List<OrderItem> orderItems = cartDto.getItems().stream()
				.map(cartItem -> modelMapper.map(cartItem, OrderItem.class)).peek(item -> item.setOrder(order))
				.collect(Collectors.toList());

		order.setItems(orderItems);

		// Save the order and get the saved instance
		Order savedOrder = orderRepository.save(order);
		cartService.clearCart(userId);

		// Convert the saved order to DTO
		return convertToDto(savedOrder);
	}

	@Override
	public OrderDto getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
		return convertToDto(order);
	}

	@Override
	public List<OrderDto> getOrdersByUserId(Long userId) {
		List<Order> orders = orderRepository.findByUserId(userId);
		return orders.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<OrderDto> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public OrderDto updateOrderStatus(Long orderId, String status) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
		order.setStatus(status);
		Order updatedOrder = orderRepository.save(order);
		return convertToDto(updatedOrder);
	}

	private OrderItem convertToOrderItem(OrderItemDto orderItemDto) {
		OrderItem orderItem = new OrderItem();
		orderItem.setProductId(orderItemDto.getProductId());
		orderItem.setProductName(orderItemDto.getProductName());
		orderItem.setQuantity(orderItemDto.getQuantity());
		orderItem.setPrice(orderItemDto.getPrice());
		orderItem.setSubTotal(orderItemDto.getSubTotal());
		return orderItem;
	}

	private OrderDto convertToDto(Order order) {
		OrderDto orderDto = modelMapper.map(order, OrderDto.class);
		orderDto.setItems(order.getItems().stream().map(this::convertItemToDto).collect(Collectors.toList()));
		return orderDto;
	}

	private OrderItemDto convertItemToDto(OrderItem orderItem) {
		return modelMapper.map(orderItem, OrderItemDto.class);
	}
}
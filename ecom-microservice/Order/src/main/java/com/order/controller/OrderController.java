package com.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.OrderDto;
import com.order.entity.User;
import com.order.repository.UserRepository;
import com.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired 
    private UserRepository userRepository;
    
    private Integer getUserIdFromUserDetails(UserDetails userDetails) {
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + userDetails.getUsername()));
        return user.getUserId();// assuming userId is of type Integer
    }


    @PostMapping
    public ResponseEntity<OrderDto> placeOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String shippingAddress,
            @RequestParam String paymentMethod) {
    	Long userId = getUserIdFromUserDetails(userDetails).longValue();
        OrderDto orderDto = orderService.placeOrder(userId, shippingAddress, paymentMethod);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId) {
    	Long userId = getUserIdFromUserDetails(userDetails).longValue();
    	OrderDto orderDto = orderService.getOrderById(orderId);
        
        // Verify that the order belongs to the user (unless admin)
        if (!orderDto.getUserId().equals(userId) && !userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return ResponseEntity.status(403).build();
        }
        
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
    	Long userId = getUserIdFromUserDetails(userDetails).longValue();
    	List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
        // Only admin can access all orders
        if (userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return ResponseEntity.status(403).build();
        }
        
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId,
            @RequestParam String status) {
        // Only admin can update order status
        if (userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return ResponseEntity.status(403).build();
        }
        
        OrderDto orderDto = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(orderDto);
    }
}
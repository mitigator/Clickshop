package com.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.CartDto;
import com.order.dto.CartItemDto;
import com.order.entity.User;
import com.order.repository.UserRepository;
import com.order.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @Autowired 
    private UserRepository userRepository;
    
    private Integer getUserIdFromUserDetails(UserDetails userDetails) {
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + userDetails.getUsername()));
        return user.getUserId();// assuming userId is of type Integer
    }


    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
    	 Integer userId = getUserIdFromUserDetails(userDetails);
         CartDto cartDto = cartService.getCartByUserId(userId.longValue());
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/items")
    public ResponseEntity<CartDto> addItemToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CartItemDto cartItemDto) {
        Long userId = getUserIdFromUserDetails(userDetails).longValue();
        CartDto cartDto = cartService.addItemToCart(userId, cartItemDto);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartDto> updateCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        Long userId = getUserIdFromUserDetails(userDetails).longValue();
        CartDto cartDto = cartService.updateCartItem(userId, productId, quantity);
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId) {
        Long userId = getUserIdFromUserDetails(userDetails).longValue();
        cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails).longValue();
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
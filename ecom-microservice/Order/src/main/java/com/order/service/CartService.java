package com.order.service;

import com.order.dto.CartDto;
import com.order.dto.CartItemDto;

public interface CartService {
	 	CartDto getCartByUserId(Long userId);
	    CartDto addItemToCart(Long userId, CartItemDto cartItemDto);
	    CartDto updateCartItem(Long userId, Long productId, Integer quantity);
	    void removeItemFromCart(Long userId, Long productId);
	    void clearCart(Long userId);

}

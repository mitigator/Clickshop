package com.order.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.dto.CartDto;
import com.order.dto.CartItemDto;
import com.order.entity.Cart;
import com.order.entity.CartItem;
import com.order.exception.ResourceNotFoundException;
import com.order.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements  CartService {

	@Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public CartDto getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setItems(new HashSet<>());
            cart.setTotalPrice(0.0);
            cart = cartRepository.save(cart);
        }
        return convertToDto(cart);
    }
    
    @Override
    public CartDto addItemToCart(Long userId, CartItemDto cartItemDto) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setItems(new HashSet<>());
            cart.setTotalPrice(0.0);
        }
        
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(cartItemDto.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItemDto.getQuantity());
            item.setSubTotal(item.getPrice() * item.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductId(cartItemDto.getProductId());
            newItem.setProductName(cartItemDto.getProductName());
            newItem.setPrice(cartItemDto.getPrice());
            newItem.setQuantity(cartItemDto.getQuantity());
            newItem.setSubTotal(cartItemDto.getPrice() * cartItemDto.getQuantity());
            cart.getItems().add(newItem);
        }

        updateCartTotal(cart);
        cart = cartRepository.save(cart);
        return convertToDto(cart);
    }
    
    @Override
    public CartDto updateCartItem(Long userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for user: " + userId);
        }

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        item.setQuantity(quantity);
        item.setSubTotal(item.getPrice() * quantity);
        updateCartTotal(cart);
        cart = cartRepository.save(cart);
        return convertToDto(cart);
    }
    
    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cart.getItems().clear();
            cart.setTotalPrice(0.0);
            cartRepository.save(cart);
        }
    }

    private void updateCartTotal(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
        cart.setTotalPrice(total);
    }

    private CartDto convertToDto(Cart cart) {
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        cartDto.setItems(cart.getItems().stream()
                .map(this::convertItemToDto)
                .collect(Collectors.toSet()));
        return cartDto;
    }

    private CartItemDto convertItemToDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDto.class);
    }

    @Override
    public void removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));
            if (removed) {
                updateCartTotal(cart);
                cartRepository.save(cart);
            }
        }
    }

}

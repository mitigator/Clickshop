package com.order.dto;

import java.util.Set;

import lombok.Data;

@Data
public class CartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> items;
    private Double totalPrice;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Set<CartItemDto> getItems() {
		return items;
	}
	public void setItems(Set<CartItemDto> items) {
		this.items = items;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public CartDto(Long id, Long userId, Set<CartItemDto> items, Double totalPrice) {
		super();
		this.id = id;
		this.userId = userId;
		this.items = items;
		this.totalPrice = totalPrice;
	}
	public CartDto() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}

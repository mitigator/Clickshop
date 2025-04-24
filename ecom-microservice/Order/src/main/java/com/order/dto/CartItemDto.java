package com.order.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Double subTotal;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	public CartItemDto(Long productId, String productName, Double price, Integer quantity, Double subTotal) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.subTotal = subTotal;
	}
	public CartItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}

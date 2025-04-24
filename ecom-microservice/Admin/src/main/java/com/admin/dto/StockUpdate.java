package com.admin.dto;

import lombok.Data;

@Data
public class StockUpdate {
    private Integer quantity;
    private String notes;
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
    
    
}
package com.admin.dto;

import lombok.Data;

@Data
public class OrderStatusUpdate {
    private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
package com.admin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer currentStock;
    private Integer restockThreshold;
    private Boolean restockAlert;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(Integer currentStock) {
		this.currentStock = currentStock;
	}
	public Integer getRestockThreshold() {
		return restockThreshold;
	}
	public void setRestockThreshold(Integer restockThreshold) {
		this.restockThreshold = restockThreshold;
	}
	public Boolean getRestockAlert() {
		return restockAlert;
	}
	public void setRestockAlert(Boolean restockAlert) {
		this.restockAlert = restockAlert;
	}
    
    
}
package com.admin.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer quantityChanged;
    private String movementType; // RESTOCK, SALE, ADJUSTMENT
    private LocalDateTime movementDate;
    private String notes;
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
	public Integer getQuantityChanged() {
		return quantityChanged;
	}
	public void setQuantityChanged(Integer quantityChanged) {
		this.quantityChanged = quantityChanged;
	}
	public String getMovementType() {
		return movementType;
	}
	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}
	public LocalDateTime getMovementDate() {
		return movementDate;
	}
	public void setMovementDate(LocalDateTime movementDate) {
		this.movementDate = movementDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
    
    
}
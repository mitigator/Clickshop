package com.admin.dto;

import lombok.Data;
import java.util.Set;

@Data
public class ProductDto {
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String imageUrl;
    private Set<Long> categoryIds;
    private Set<Long> tagIds;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Set<Long> getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(Set<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}
	public Set<Long> getTagIds() {
		return tagIds;
	}
	public void setTagIds(Set<Long> tagIds) {
		this.tagIds = tagIds;
	}
    
    
}
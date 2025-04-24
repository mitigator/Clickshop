package com.admin.service;

import com.admin.dto.ProductDto;
import com.admin.entity.Product;
import com.admin.exception.ResourceNotFoundException;
import com.admin.repository.CategoryRepository;
import com.admin.repository.ProductRepository;
import com.admin.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    public Product createProduct(ProductDto productDto) {
        Product product = new Product();
        mapDtoToEntity(productDto, product);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        mapDtoToEntity(productDto, product);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    private void mapDtoToEntity(ProductDto dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStockQuantity(dto.getStockQuantity());
        entity.setImageUrl(dto.getImageUrl());
        
        if (dto.getCategoryIds() != null) {
            entity.setCategories(dto.getCategoryIds().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new ResourceNotFoundException("Category not found")))
                    .collect(Collectors.toSet()));
        }
        
        if (dto.getTagIds() != null) {
            entity.setTags(dto.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tag not found")))
                    .collect(Collectors.toSet()));
        }
    }
}
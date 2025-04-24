// src/main/java/com/admin/service/InventoryService.java
package com.admin.service;

import com.admin.dto.StockUpdate;
import com.admin.entity.Inventory;
import com.admin.entity.StockMovement;
import com.admin.exception.ResourceNotFoundException;
import com.admin.repository.InventoryRepository;
import com.admin.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Transactional
    public Inventory restockProduct(Long productId, StockUpdate stockUpdate) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setProductId(productId);
                    newInventory.setCurrentStock(0);
                    return newInventory;
                });
        
        int newStock = inventory.getCurrentStock() + stockUpdate.getQuantity();
        inventory.setCurrentStock(newStock);
        
        // Check if we need to clear restock alert
        if (inventory.getRestockThreshold() != null && 
            newStock > inventory.getRestockThreshold()) {
            inventory.setRestockAlert(false);
        }
        
        Inventory savedInventory = inventoryRepository.save(inventory);
        
        // Record stock movement
        StockMovement movement = new StockMovement();
        movement.setProductId(productId);
        movement.setQuantityChanged(stockUpdate.getQuantity());
        movement.setMovementType("RESTOCK");
        movement.setMovementDate(LocalDateTime.now());
        movement.setNotes(stockUpdate.getNotes());
        stockMovementRepository.save(movement);
        
        return savedInventory;
    }

    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findByRestockAlertTrue();
    }

    public List<StockMovement> getStockMovementHistory(Long productId) {
        return stockMovementRepository.findByProductId(productId);
    }
}
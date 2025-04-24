package com.admin.controller;

import com.admin.dto.StockUpdate;
import com.admin.entity.Inventory;
import com.admin.entity.StockMovement;
import com.admin.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/{productId}/restock")
    public ResponseEntity<Inventory> restockProduct(
            @PathVariable Long productId,
            @RequestBody StockUpdate stockUpdate) {
        Inventory inventory = inventoryService.restockProduct(productId, stockUpdate);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Inventory>> getLowStockItems() {
        List<Inventory> lowStockItems = inventoryService.getLowStockItems();
        return ResponseEntity.ok(lowStockItems);
    }

    @GetMapping("/{productId}/history")
    public ResponseEntity<List<StockMovement>> getStockMovementHistory(
            @PathVariable Long productId) {
        List<StockMovement> history = inventoryService.getStockMovementHistory(productId);
        return ResponseEntity.ok(history);
    }
}
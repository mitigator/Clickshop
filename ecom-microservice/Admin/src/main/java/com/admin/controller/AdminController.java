package com.admin.controller;

import com.admin.dto.UserRoleUpdate;
import com.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/{userId}/role")
    public ResponseEntity<String> updateUserRole(
            @PathVariable Long userId,
            @RequestBody UserRoleUpdate roleUpdate,
            @RequestHeader("Authorization") String authToken) {
        return adminService.updateUserRole(userId, roleUpdate, authToken);
    }
}
package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ecom.entity.User;
import com.ecom.service.UserService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api") // Optional but recommended for consistent API prefixing
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping("/registerNewUser")
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping("/forAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String forAdmin() {
        return "This URL is only accessible to the admin";
    }

    @GetMapping("/forUser")
    @PreAuthorize("hasRole('USER')")
    public String forUser() {
        return "This URL is only accessible to the user";
    }
}

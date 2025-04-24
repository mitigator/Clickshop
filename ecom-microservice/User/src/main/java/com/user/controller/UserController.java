package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.config.SimpleUserDetails;
import com.user.dto.PasswordResetRequest;
import com.user.dto.UserProfileDTO;
import com.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
public class UserController {
	@Autowired
    private UserService userService;
    
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SimpleUserDetails userDetails = (SimpleUserDetails) auth.getPrincipal();
        
        UserProfileDTO profile = userService.getUserProfile(userDetails.getUserId());
        return ResponseEntity.ok(profile);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDTO> updateUserProfile(@RequestBody UserProfileDTO profileDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SimpleUserDetails userDetails = (SimpleUserDetails) auth.getPrincipal();
        
        UserProfileDTO updatedProfile = userService.updateUserProfile(userDetails.getUserId(), profileDTO);
        return ResponseEntity.ok(updatedProfile);
    }
    
    
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest resetRequest) {
    	System.out.println("resetting password");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SimpleUserDetails userDetails = (SimpleUserDetails) auth.getPrincipal();
        System.out.println(userDetails.getUsername());
        boolean success = userService.resetPassword(resetRequest);
        
        if (success) {
            return ResponseEntity.ok().body("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}

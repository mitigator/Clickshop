package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.dto.PasswordResetRequest;
import com.user.dto.UserProfileDTO;
import com.user.entity.User;
import com.user.exception.UserNotFoundException;
import com.user.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserProfileDTO getUserProfile(int userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        return convertToDTO(user);
    }
    
    public UserProfileDTO updateUserProfile(int userId, UserProfileDTO profileDTO) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        if (profileDTO.getName() != null) {
            user.setName(profileDTO.getName());
        }
        
        if (profileDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(profileDTO.getPhoneNumber());
        }
        
        if (profileDTO.getAddress() != null) {
            user.setAddress(profileDTO.getAddress());
        }
        
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }
    
    public boolean resetPassword(PasswordResetRequest resetRequest) {
        User user = userRepository.findByEmail(resetRequest.getEmail())
            .orElseThrow(() -> new UserNotFoundException("User not found with email: " + resetRequest.getEmail()));
        
        if (!passwordEncoder.matches(resetRequest.getOldPassword(), user.getPassword())) {
            return false;
        }
        
        user.setPassword(passwordEncoder.encode(resetRequest.getNewPassword()));
        userRepository.save(user);
        return true;
    }
    
    private UserProfileDTO convertToDTO(User user) {
        return new UserProfileDTO(
            user.getUserId(),
            user.getName(),
            user.getUserName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getAddress()
        );
    }
}
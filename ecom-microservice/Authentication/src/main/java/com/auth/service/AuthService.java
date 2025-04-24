package com.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.entity.User;
import com.auth.repository.AuthenticationRepository;

@Service
public class AuthService implements UserDetailsService {
    
    private final AuthenticationRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public AuthService(AuthenticationRepository authRepository, 
                      PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public String register(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = authRepository.save(user);
            return "User Registered Successfully. ID: " + savedUser.getUserId();
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        System.out.println(user.getUserName()+" "+user.getPassword()+" "+user.getRole());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole().name().split(","))
                .build();
    }
    
    public User getUserByUsername(String username) {
        return authRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
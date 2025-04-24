package com.auth.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.entity.User;
import com.auth.repository.AuthenticationRepository;
import com.auth.service.AuthService;
import com.auth.util.AuthResponse;
import com.auth.util.JWTUtil;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationRepository authRepository;

    @GetMapping("hello")
    public String getMethodName() {
        return "hello";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        System.out.println("post /auth");
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = authRepository.save(user);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User Registered Successfully",
                "userId", savedUser.getUserId()
            ));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody User user) throws Exception {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            final User userEntity = authService.getUserByUsername(user.getUserName());

            System.out.println("Extracted user details - " + "Username: " + userEntity.getUserName() + ", "
                    + "User ID: " + userEntity.getUserId() + ", " + "Role: " + userEntity.getRole().name());

            List<String> roles = List.of(userEntity.getRole().name());
            return new AuthResponse(jwtUtil.generateToken(userDetails.getUsername(), userEntity.getUserId(), roles),
                    userEntity.getUserId(), userEntity.getRole().name());

        } catch (AuthenticationException e) {
            System.out.println("You entered invalid user name or password");
            throw new Exception("Invalid username or password", e);
        }
    }
}
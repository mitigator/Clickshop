package com.admin.service;

import com.admin.dto.UserRoleUpdate;
import com.admin.exception.AdminAccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminService {

    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${auth.service.url}")
    private String authServiceUrl;

    public ResponseEntity<String> updateUserRole(Long userId, UserRoleUpdate roleUpdate, String authToken) {
        // Verify the requester is an admin
        if (!isAdmin(authToken)) {
            throw new AdminAccessDeniedException("Only admins can update user roles");
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<UserRoleUpdate> request = new HttpEntity<>(roleUpdate, headers);
        
        return restTemplate.exchange(
            authServiceUrl + "/users/" + userId + "/role",
            HttpMethod.PUT,
            request,
            String.class
        );
    }

    private boolean isAdmin(String authToken) {
        // This would call your auth service to verify the token and check roles
        // For now, we'll assume it's implemented
        return true;
    }
}
package com.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(int userId);
    Optional<User> findByUserName(String username);

    
}
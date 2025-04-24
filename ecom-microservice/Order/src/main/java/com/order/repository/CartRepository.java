package com.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.entity.Cart;

public interface CartRepository extends  JpaRepository<Cart, Long>{
    Cart findByUserId(Long userId);


}

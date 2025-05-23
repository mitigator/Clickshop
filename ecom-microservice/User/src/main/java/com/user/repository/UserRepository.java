package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
		Optional<User> findByUserName(String userName);
	    Optional<User> findByEmail(String email);
	    Optional<User> findByPhoneNumber(Long phoneNumber);
	

}

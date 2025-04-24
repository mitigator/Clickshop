package com.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.User;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String authRepo);


}

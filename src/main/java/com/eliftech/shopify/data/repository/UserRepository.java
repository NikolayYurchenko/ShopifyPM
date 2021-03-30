package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

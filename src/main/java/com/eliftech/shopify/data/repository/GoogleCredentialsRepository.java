package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.GoogleCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleCredentialsRepository extends JpaRepository<GoogleCredentials, Long> {
}

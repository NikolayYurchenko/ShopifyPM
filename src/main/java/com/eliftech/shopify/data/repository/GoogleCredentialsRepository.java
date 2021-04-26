package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.GoogleCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoogleCredentialsRepository extends JpaRepository<GoogleCredentials, Long> {

    Optional<GoogleCredentials> findTop1ByOrderByCreatedAt();
}

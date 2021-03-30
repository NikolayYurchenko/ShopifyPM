package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductDataRepository extends JpaRepository<ProductData, Long> {

    Optional<ProductData> findByUuid(UUID productDataUid);
}

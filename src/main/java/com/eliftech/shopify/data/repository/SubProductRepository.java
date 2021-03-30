package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.SubProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubProductRepository extends JpaRepository<SubProduct, Long> {

    Optional<SubProduct> findByUuid(UUID subProductUid);
}

package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByName(String storeName);

    List<Store> findByProductsUuid(UUID productUuid);
}

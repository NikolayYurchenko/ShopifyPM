package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findTop1ByOrderByCreatedAtDesc();

    Page<Order> findAllByStoreUid(String storeUid, Pageable pageable);
}

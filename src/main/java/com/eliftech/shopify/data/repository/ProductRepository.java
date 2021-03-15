package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

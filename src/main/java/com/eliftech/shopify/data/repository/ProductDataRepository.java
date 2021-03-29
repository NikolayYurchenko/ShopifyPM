package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataRepository extends JpaRepository<ProductData, Long> {


}

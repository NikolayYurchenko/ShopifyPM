package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p.* from products p inner join store_product sp on p.id = sp.product_id inner join stores s on sp.store_id = s.id where s.uuid = ?1 order by p.created_at desc", nativeQuery = true)
    Optional<Product> findLastProductByStore(String storeUid);

    Page<Product> findByStoresUuid(UUID storeUid, Pageable pageable);

    Optional<Product> findByHandle(String handle);

    Optional<Product> findByUuid(UUID productUid);
}

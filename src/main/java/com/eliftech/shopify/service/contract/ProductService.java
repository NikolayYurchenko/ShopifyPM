package com.eliftech.shopify.service.contract;


import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.model.ProductResponse;
import com.eliftech.shopify.rest.model.ProductRestResponse;

import java.util.List;

public interface ProductService {

    /**
     * Sync products by store name
     * @param storeName
     */
    void sync(String storeName);

    /**
     * Find all products
     * @param storeName
     * @param page
     * @param limit
     * @return
     */
    List<ProductResponse> findAll(String storeName, int page, int limit);
}

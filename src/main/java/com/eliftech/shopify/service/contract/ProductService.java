package com.eliftech.shopify.service.contract;


import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.model.ProductResponse;
import com.eliftech.shopify.model.ProductUpdateForm;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import com.eliftech.shopify.rest.model.UpdateProductRequest;

import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * Sync products by store name
     * @param storeName
     */
    void sync(String storeName);

    /**
     * Sync certain product
     * @param storeName
     * @param productUid
     */
    void syncCertain(String storeName, String productUid);

    /**
     *
     * @param storeName
     * @param productUid
     * @param request
     * @return
     */
    ProductResponse update(String storeName, String productUid, UpdateProductRequest request);

    /**
     * Find all products
     * @param storeName
     * @param page
     * @param limit
     * @return
     */
    List<ProductResponse> findAll(String storeName, int page, int limit);

    /**
     * Find product by uuid
     * @param productUid
     * @return
     */
    Map<String, ProductResponse> findByUuid(String productUid);
}

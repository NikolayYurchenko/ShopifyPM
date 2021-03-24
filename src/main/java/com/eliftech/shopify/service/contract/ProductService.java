package com.eliftech.shopify.service.contract;


public interface ProductService {

    /**
     * Sync products by store name
     * @param storeName
     */
    void sync(String storeName);
}

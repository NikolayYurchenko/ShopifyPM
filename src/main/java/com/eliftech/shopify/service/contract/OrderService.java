package com.eliftech.shopify.service.contract;

public interface OrderService {

    /**
     * Sync orders
     * @param storeName
     */
    void sync(String storeName);
}

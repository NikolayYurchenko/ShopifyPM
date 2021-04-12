package com.eliftech.shopify.service.contract;

import com.eliftech.shopify.model.OrderResponse;
import com.eliftech.shopify.model.ProductResponse;

import java.util.List;

public interface OrderService {

    /**
     * Sync orders
     * @param storeName
     */
    void sync(String storeName);

    /**
     * Find all orders by store
     * @param storeName
     * @param page
     * @param limit
     * @return
     */
    List<OrderResponse> findAll(String storeName, int page, int limit);
}

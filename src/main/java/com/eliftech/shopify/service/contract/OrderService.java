package com.eliftech.shopify.service.contract;

import com.eliftech.shopify.model.AbstractApiResponse;
import com.eliftech.shopify.model.OrderResponse;
import org.springframework.lang.Nullable;

import java.util.List;

public interface OrderService {

    /**
     *  Sync orders
     * @param storeName
     * @param sinceId
     * @return
     */
    AbstractApiResponse sync(String storeName, @Nullable String sinceId);

    /**
     * Find all orders by store
     * @param storeName
     * @param page
     * @param limit
     * @return
     */
    List<OrderResponse> findAll(String storeName, int page, int limit);
}

package com.eliftech.shopify.service;

import com.eliftech.shopify.data.entity.Order;
import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.entity.SubProduct;
import com.eliftech.shopify.data.service.OrderDataService;
import com.eliftech.shopify.data.service.StoreDataService;
import com.eliftech.shopify.data.service.SubProductDataService;
import com.eliftech.shopify.model.*;
import com.eliftech.shopify.rest.ShopifyRestRepository;
import com.eliftech.shopify.rest.model.OrderRestResponse;
import com.eliftech.shopify.service.contract.GoogleApp;
import com.eliftech.shopify.service.contract.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final StoreDataService storeDataService;
    private final OrderDataService orderDataService;
    private final ShopifyRestRepository shopifyRestRepository;
    private final SubProductDataService subProductDataService;
    private final GoogleApp googleApp;


    @Override
    @Transactional
    public AbstractApiResponse sync(String storeName) {

        Store store = storeDataService.findByName(storeName);

        Optional<String> lastOrderCreateDate = orderDataService.getCreatedAtFromLastOrder();

        List<OrderRestResponse> orders = shopifyRestRepository.getOrders(storeName, lastOrderCreateDate.orElse(null), store.getPassword());

        List<OrderSheetRecord> records = new ArrayList<>();

        List<String> existingOrdersIds = orderDataService.findByExternalIds(orders.stream()
                    .map(OrderRestResponse::getId)
                    .collect(Collectors.toList()))
                .stream().map(Order::getExternalId).collect(Collectors.toList());

        orders.forEach(order -> order.getLineItems().forEach(orderItem -> {

            Optional<SubProduct> subProduct = subProductDataService.findByExternalId(orderItem.getVariantId());

            if (subProduct.isPresent() && !existingOrdersIds.contains(order.getId()))

            subProduct.ifPresent(subProductItem ->
                    records.add(OrderSheetRecord
                     .instance(order, subProductItem, orderItem.getSku(), order.defineFactoryBySku(orderItem.getSku()))));
        }));

        try {

            googleApp.executeAction(records);

            List<String> ordersIds = records.stream().map(OrderSheetRecord::getExternalId).collect(Collectors.toList());

            List<OrderRestResponse> ordersForCreate = orders.stream()
                    .filter(order -> ordersIds.contains(order.getId())).collect(Collectors.toList());

            this.createOrders(ordersForCreate, store.getUuid().toString());

            return SuccessApiResponse.instance();

        } catch (Exception e) {

            return ForbiddenApiResponse.instance();
        }
    }

    private void createOrders(List<OrderRestResponse> orders, String storeUid) {

        orderDataService.create(orders, storeUid);
    }

    @Override
    public List<OrderResponse> findAll(String storeName, int page, int limit) {

        Store store = storeDataService.findByName(storeName);

        List<Order> orders = orderDataService.findAllByStoreUid(store.getUuid().toString(), page, limit);

        return OrderResponse.instance(orders);
    }
}

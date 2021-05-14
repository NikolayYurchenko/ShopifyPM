package com.eliftech.shopify.service;

import com.eliftech.shopify.data.entity.Order;
import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.entity.SubProduct;
import com.eliftech.shopify.data.service.OrderDataService;
import com.eliftech.shopify.data.service.StoreDataService;
import com.eliftech.shopify.data.service.SubProductDataService;
import com.eliftech.shopify.model.*;
import com.eliftech.shopify.rest.ShopifyRestRepository;
import com.eliftech.shopify.rest.model.OrderItem;
import com.eliftech.shopify.rest.model.OrderRestResponse;
import com.eliftech.shopify.service.contract.GoogleApp;
import com.eliftech.shopify.service.contract.OrderService;
import com.eliftech.shopify.util.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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
    public AbstractApiResponse sync(String storeName, @Nullable String sinceId) {

        Store store = storeDataService.findByName(storeName);

        List<OrderRestResponse> orders = Collections.emptyList();

        if (Objects.isNull(sinceId) || sinceId.isBlank()) {

            Optional<String> lastOrderCreateDate = orderDataService.getCreatedAtFromLastOrder();

            orders = shopifyRestRepository.getOrdersAfterSpecificDate(storeName, lastOrderCreateDate.orElse(null), store.getPassword());

        } else {

            orders = shopifyRestRepository.getOrdersAfterSpecificSinceId(storeName, sinceId, store.getPassword());
        }

        List<OrderSheetRecord> records = new ArrayList<>();

        List<String> existingOrdersIds = orderDataService.findByExternalIds(orders.stream()
                    .map(OrderRestResponse::getId)
                    .collect(Collectors.toList()))
                .stream().map(Order::getExternalId).collect(Collectors.toList());

        orders.forEach(order -> {

            List<OrderItem> orderItems = order.getLineItems();

            Set<FactoryType> factoryTypes = orderItems.stream().map(OrderItem::getSku).map(OrderUtil::defineFactoryBySku).collect(Collectors.toSet());

            orderItems.forEach(orderItem -> {

                Optional<SubProduct> subProduct = subProductDataService.findByExternalId(orderItem.getVariantId());

                boolean isAlreadyAdded = records.stream().map(OrderSheetRecord::getExternalId).collect(Collectors.toList()).contains(order.getId());

                boolean isNeedAddLetter = factoryTypes.size() > 1;

                if (subProduct.isPresent() && !isAlreadyAdded && !existingOrdersIds.contains(order.getId())) {

                    records.add(OrderSheetRecord
                            .instance(order, subProduct.get(), orderItem.getSku(), OrderUtil.defineFactoryBySku(orderItem.getSku()), isNeedAddLetter));
                }
            });
        });

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

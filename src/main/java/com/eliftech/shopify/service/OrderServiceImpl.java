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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        List<OrderSheetRecord> records = this.createOrders(orders, store.getUuid().toString());

        try {

            googleApp.executeAction(records);

            return SuccessApiResponse.instance();

        } catch (Exception e) {

            return ForbiddenApiResponse.instance();
        }
    }

    private  List<OrderSheetRecord> createOrders( List<OrderRestResponse> orders, String storeUid) {

        List<OrderRestResponse> availableForCreate = new ArrayList<>();

        List<OrderSheetRecord> records = new ArrayList<>();

        orders.forEach(order -> {

            List<OrderItem> orderItems = order.getLineItems();

//            List<FactoryType> factoryTypes = new ArrayList<>();
//
//            order.getLineItems().forEach(item -> factoryTypes.add(order.defineFactoryBySku(item.getSku())));

//            boolean isNeedAddLetter = order.getLineItems().size() > 1 && factoryTypes.stream().reduce(())

             orderItems.forEach(orderItem -> {

                 try {

                     SubProduct subProduct = subProductDataService.findByExternalId(orderItem.getVariantId());

                     records.add(OrderSheetRecord
                              .instance(order, subProduct, orderItem.getSku(), order.defineFactoryBySku(orderItem.getSku())));

                     if (!availableForCreate.contains(order)) {
                          availableForCreate.add(order);
                      }

                 } catch (EntityNotFoundException e) {

                         log.info(e.getMessage());
                 }
            });
        });

       orderDataService.create(availableForCreate, storeUid);

       return records;
    }

    @Override
    public List<OrderResponse> findAll(String storeName, int page, int limit) {

        Store store = storeDataService.findByName(storeName);

        List<Order> orders = orderDataService.findAllByStoreUid(store.getUuid().toString(), page, limit);

        return OrderResponse.instance(orders);
    }
}

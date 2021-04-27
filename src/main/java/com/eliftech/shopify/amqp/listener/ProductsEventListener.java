package com.eliftech.shopify.amqp.listener;


import com.eliftech.shopify.amqp.publisher.ProductSyncRequest;
import com.eliftech.shopify.config.AmqpConnectionConfig;
import com.eliftech.shopify.model.AbstractApiResponse;
import com.eliftech.shopify.model.ForbiddenApiResponse;
import com.eliftech.shopify.model.SuccessApiResponse;
import com.eliftech.shopify.service.contract.OrderService;
import com.eliftech.shopify.service.contract.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsEventListener {

    private final ProductService productService;
    private final OrderService orderService;

    @RabbitListener(queues = AmqpConnectionConfig.SYNC_PRODUCTS_QUEUE)
    public void handleProductsSync(String storeName) {

        try {

            log.info("Receive event about sync products by store name:[{}]", storeName);

            productService.sync(storeName);

        } catch (Exception e) {
            
            log.error("Failed sync products from store:[{}], cause:[{}]", storeName, e.getMessage());
        }
    }

    @RabbitListener(queues = AmqpConnectionConfig.SYNC_SINGLE_PRODUCT_QUEUE)
    public void handleSingleProductSync(ProductSyncRequest request) {

        try {

            log.info("Receive event about sync product by store name:[{}]", request.getStoreName());

            productService.syncCertain(request.getStoreName(), request.getProductUid());

        } catch (Exception e) {

            log.error("Failed sync product from store:[{}], cause:[{}]", request.getStoreName(), e.getMessage());
        }
    }

    @RabbitListener(queues = AmqpConnectionConfig.SYNC_ORDERS_QUEUE)
    public AbstractApiResponse handleOrdersSync(String storeName) {

        try {

            log.info("Receive event about sync orders by store name:[{}]", storeName);

            orderService.sync(storeName);

            return SuccessApiResponse.instance();

        } catch (Exception e) {

            log.error("Failed sync orders from store:[{}], cause:[{}]", storeName, e.getMessage());

            return ForbiddenApiResponse.instance();
        }
    }
}

package com.eliftech.shopify.amqp.listener;


import com.eliftech.shopify.config.AmqpConnectionConfig;
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

    @RabbitListener(queues = AmqpConnectionConfig.SYNC_PRODUCT_QUEUE)
    public void handleProductSync(String storeName) {

        try {

            log.info("Receive event about sync products by store name:[{}]", storeName);

            productService.sync(storeName);

        } catch (Exception e) {
            
            log.error("Failed sync products from store:[{}], cause:[{}]", storeName, e.getMessage());
        }
    }
}

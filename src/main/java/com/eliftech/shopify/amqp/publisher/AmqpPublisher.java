package com.eliftech.shopify.amqp.publisher;

import com.eliftech.shopify.config.AmqpConnectionConfig;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AmqpPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Notify about product sync
     * @param storeName
     */
    public void notifyProductSync(String storeName) {

        log.info("Publish event to sync products by store:[{}]", storeName);

        rabbitTemplate.convertAndSend(AmqpConnectionConfig.SYNC_PRODUCT_QUEUE, storeName);
    }
}

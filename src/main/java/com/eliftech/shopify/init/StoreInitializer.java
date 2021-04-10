package com.eliftech.shopify.init;

import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.service.StoreDataService;
import com.eliftech.shopify.init.util.StaticModelBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@ConditionalOnProperty(value = "shopify.init.enabled")
public class StoreInitializer {

    @Autowired
    private StoreDataService storeDataService;

    @EventListener(value = ApplicationStartedEvent.class)
    public void init() {

        log.info("Start init stores...");

        List<Store> stores = storeDataService.findAll();

        if (stores.isEmpty()) {
            StaticModelBuilder.getStores().forEach(storeDataService::create);
        }
    }
}

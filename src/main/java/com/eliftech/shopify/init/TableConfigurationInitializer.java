package com.eliftech.shopify.init;

import com.eliftech.shopify.data.entity.TableConfiguration;
import com.eliftech.shopify.data.service.TableConfigurationDataService;
import com.eliftech.shopify.init.util.StaticModelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(value = "shopify.init.enabled")
public class TableConfigurationInitializer {

    @Autowired
    private TableConfigurationDataService configurationDataService;

    @EventListener(value = ApplicationStartedEvent.class)
    public void init() {

        List<TableConfiguration> configurations = configurationDataService.findAll();

        if (configurations.isEmpty()) {
            StaticModelBuilder.getTableConfig().forEach((factoryType, tableUid) -> configurationDataService.create(tableUid, factoryType));
        }
    }
}

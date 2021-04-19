package com.eliftech.shopify.init;

import com.eliftech.shopify.data.entity.GoogleCredentials;
import com.eliftech.shopify.data.service.GoogleCredentialsDataService;
import com.eliftech.shopify.init.util.StaticModelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@ConditionalOnProperty(value = "shopify.init.enabled")
public class GoogleCredentialsInitializer {

    @Autowired
    private GoogleCredentialsDataService credentialsDataService;

    @EventListener(value = ApplicationStartedEvent.class)
    public void init() {

        log.info("Start init google credentials...");

        List<GoogleCredentials> credentials = credentialsDataService.findAll();

        if (credentials.isEmpty()) {
            StaticModelBuilder.getGoogleCredentials().forEach((email, apiKey) -> credentialsDataService.create(email, apiKey));
        }
    }
}

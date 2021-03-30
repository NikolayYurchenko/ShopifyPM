package com.eliftech.shopify.init;

import com.eliftech.shopify.data.service.UserDataService;
import com.eliftech.shopify.init.util.StaticModelBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@ConditionalOnProperty(value = "shopify.init.enabled")
public class UserInitializer {

    private final UserDataService userDataService;

    @EventListener(value = ApplicationStartedEvent.class)
    public void init() {

        userDataService.create(StaticModelBuilder.getUser());
    }
}

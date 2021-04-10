package com.eliftech.shopify.init;

import com.eliftech.shopify.data.entity.User;
import com.eliftech.shopify.data.service.UserDataService;
import com.eliftech.shopify.init.util.StaticModelBuilder;
import lombok.AllArgsConstructor;
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
public class UserInitializer {

    @Autowired
    private UserDataService userDataService;

    @EventListener(value = ApplicationStartedEvent.class)
    public void init() {

        List<User> users = userDataService.findAll();

        if (users.isEmpty()) {
            userDataService.create(StaticModelBuilder.getUser());
        }
    }
}

package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.repository.StoreRepository;
import com.eliftech.shopify.model.StoreForm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StoreDataService {

    private final StoreRepository storeRepository;

    /**
     * Create store
     * @param request
     */
    public void create(StoreForm request) {

        log.info("Creating store from data:[{}]", request);

        Store store = Store.builder()
                .uuid(UUID.randomUUID())
                .apiKey(request.getApiKey())
                .name(request.getName())
                .products(Collections.emptyList())
                .build();

        storeRepository.save(store);
    }

    /**
     * Find store by store name
     * @param storeName
     * @return
     */
    public Store findByName(String storeName) {

        log.info("Searching store by name:[{}]", storeName);

        Store store = storeRepository.findByName(storeName)
                .orElseThrow(() -> new EntityNotFoundException("Not found store by name:["+ storeName +"]"));

        log.info("...found:[{}]", store);

        return store;
    }
}

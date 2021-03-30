package com.eliftech.shopify.service;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.ProductData;
import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.service.ProductDataService;
import com.eliftech.shopify.data.service.StoreDataService;
import com.eliftech.shopify.model.ProductResponse;
import com.eliftech.shopify.rest.ShopifyRestRepository;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import com.eliftech.shopify.service.contract.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    private final ProductDataService productDataService;
    private final StoreDataService storeDataService;
    private final ShopifyRestRepository shopifyRestRepository;

    @Override
    @Transactional
    public void sync(String storeName) {

        Store store = storeDataService.findByName(storeName);

        List<ProductRestResponse> products = shopifyRestRepository.getActualProducts(storeName, store.getPassword());

        List<String> existStates = new ArrayList<>();

        products.forEach(product -> {

            Optional<Product> existProductByHandle = productDataService.findByHandle(product.getHandle());

            existProductByHandle.ifPresent(existProduct -> {

                List<String> relatedStores = existProduct.getStores().stream().map(s -> s.getUuid().toString()).collect(Collectors.toList());

                if (!relatedStores.contains(store.getUuid().toString())) {
                    productDataService.addState(store.getUuid().toString(), existProduct.getUuid().toString(), product);
                }

                existStates.add(product.getId());
            });
        });

        products.removeIf(p -> existStates.contains(p.getId()));

        productDataService.create(products, storeName);
    }

    @Override
    public List<ProductResponse> findAll(String storeName, int page, int limit) {

        Store store = storeDataService.findByName(storeName);

        List<Product> products = productDataService.findAll(store.getUuid().toString(), page, limit);

        List<ProductData> states = products.stream()
                .map(Product::getStates)
                .flatMap(List::stream).filter(s -> s.getStoreUid().equals(store.getUuid().toString()))
                .collect(Collectors.toList());

        return ProductResponse.instance(store.getUuid().toString(), states);
    }
}

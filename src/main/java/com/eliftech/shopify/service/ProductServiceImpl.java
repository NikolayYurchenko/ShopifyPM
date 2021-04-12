package com.eliftech.shopify.service;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.ProductData;
import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.entity.SubProduct;
import com.eliftech.shopify.data.service.ProductDataService;
import com.eliftech.shopify.data.service.StoreDataService;
import com.eliftech.shopify.model.ProductResponse;
import com.eliftech.shopify.model.ProductUpdateForm;
import com.eliftech.shopify.rest.ShopifyRestRepository;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import com.eliftech.shopify.rest.model.UpdateProductRequest;
import com.eliftech.shopify.rest.model.AbstractItem;
import com.eliftech.shopify.service.contract.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

                Optional<ProductData> alreadySyncedState = existProduct.getStates().stream()
                        .filter(s -> s.getStoreUid().equals(store.getUuid().toString()))
                        .findFirst();

                if (alreadySyncedState.isEmpty()) {
                    log.info("Product not sync for store:[{}]", store.getUuid());
                    productDataService.addState(store.getUuid().toString(), existProduct.getUuid().toString(), product);
                }

                existStates.add(product.getId());
            });
        });

        products.removeIf(p -> existStates.contains(p.getId()));

        productDataService.create(products, storeName);
    }

    @Override
    public void syncCertain(String storeName, String productUid) {

        Store store = storeDataService.findByName(storeName);

        Product product = productDataService.findByUuid(productUid);

        ProductRestResponse productRest = shopifyRestRepository.getProductById(storeName, product.getSinceId(), store.getPassword());

        productDataService.refreshState(productUid, store.getUuid().toString(), productRest);
    }

    @Override
    public ProductResponse update(String storeName, String productUid, UpdateProductRequest request) {

        Store store = storeDataService.findByName(storeName);

        Product product = productDataService.findByUuid(productUid);

        ProductRestResponse restProduct = shopifyRestRepository.updateProduct(storeName, product.getSinceId(), request, store.getPassword());

        Product updatedProduct = productDataService.update(productUid, store.getUuid().toString(), restProduct);

        return ProductResponse.instance(store.getUuid().toString(), updatedProduct.getStateByStore(store.getUuid().toString()));
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

    @Override
    public Map<String, ProductResponse> findByUuid(String productUid) {

        Product product = productDataService.findByUuid(productUid);

        return product.getStates().stream()
                .collect(Collectors.toMap(ProductData::getStoreUid, s -> ProductResponse.instance(s.getStoreUid(), s)));
    }
}

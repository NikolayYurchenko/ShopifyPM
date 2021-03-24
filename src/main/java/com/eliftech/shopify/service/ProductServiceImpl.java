package com.eliftech.shopify.service;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.service.ProductDataService;
import com.eliftech.shopify.data.service.StoreDataService;
import com.eliftech.shopify.rest.ShopifyRestRepository;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import com.eliftech.shopify.service.contract.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    private final ProductDataService productDataService;
    private final StoreDataService storeDataService;
    private final ShopifyRestRepository shopifyRestRepository;

    @Override
    public void sync(String storeName) {

        Store store = storeDataService.findByName(storeName);

        Optional<Product> product = productDataService.findLastProductByStore(store.getUuid().toString());

        List<ProductRestResponse> products = shopifyRestRepository.getActualProducts(storeName, store.getApiKey(), product.map(Product::getSinceId).orElse(null));

        productDataService.create(products, storeName);
    }
}

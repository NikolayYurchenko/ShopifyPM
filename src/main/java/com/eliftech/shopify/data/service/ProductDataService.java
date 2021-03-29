package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.ProductData;
import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.repository.ProductRepository;
import com.eliftech.shopify.rest.model.Image;
import com.eliftech.shopify.rest.model.Option;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductDataService {

    private final ProductRepository productRepository;
    private final StoreDataService storeDataService;
    private final ProductStateDataService stateDataService;

    /**
     * Create unique products
     * @param productsForm
     */
    public void create(List<ProductRestResponse> productsForm, String storeName) {

        log.info("Creating products:[{}]", productsForm.size());

        Store store = storeDataService.findByName(storeName);

        String storeUid = store.getUuid().toString();

        List<Product> products = productsForm.stream()
                .map(product -> Product.builder()
                 .uuid(UUID.randomUUID())
                 .handle(product.getHandle())
                 .sinceId(product.getId())
                 .stores(List.of(store))
                 .build()).collect(Collectors.toList());

        store.getProducts().addAll(products);

        List<Product> savedProducts = productRepository.saveAll(products);

        savedProducts.forEach(p ->
                stateDataService.create(storeUid, p, Objects.requireNonNull(productsForm.stream().filter(f -> f.getId().equals(p.getSinceId())).findFirst().orElse(null))));
    }

    /**
     * Find last product by store
     * @param storeUid
     * @return
     */
    public Optional<Product> findLastProductByStore(String storeUid) {

        log.info("Searching last product by store:[{}]", storeUid);

        Optional<Product> product = productRepository.findLastProductByStore(storeUid);

        log.info("...found:[{}]", product);

        return product;
    }

    /**
     * Find all by store
     * @param storeUid
     * @return
     */
    public List<Product> findAll(String storeUid, int page, int limit) {

        log.info("Searching  products by store:[{}]", storeUid);

        Page<Product> products = productRepository.findByStoresUuid(UUID.fromString(storeUid), PageRequest.of(page, limit));

        log.info("...found:[{}]", products.getContent().size());

        return products.getContent();
    }

    /**
     * Find all products by handle
     * @param handle
     * @return
     */
    public Optional<Product> findByHandle(String handle) {

        log.info("Searching product by handle:[{}]", handle);

        Optional<Product> product = productRepository.findByHandle(handle);

        log.info("...found:[{}]", product);

        return product;
    }

    /**
     * Find by uuid
     * @param productUid
     * @return
     */
    public Product findByUuid(String productUid) {

        log.info("Searching product by uuid:[{}]", productUid);

        Product product = productRepository.findByUuid(UUID.fromString(productUid))
                .orElseThrow(() ->  new EntityNotFoundException("Not found product by uuid:["+ productUid +"]"));

        log.info("...found:[{}]", product);

        return product;
    }

    /**
     * Add state for product
     * @param storeUid
     * @param productUid
     * @param request
     */
    public void addState(String storeUid, String productUid, ProductRestResponse request) {

        Product product = this.findByUuid(productUid);

        ProductData state = stateDataService.create(storeUid, product, request);

        product.getStates().add(state);

        productRepository.save(product);
    }
}

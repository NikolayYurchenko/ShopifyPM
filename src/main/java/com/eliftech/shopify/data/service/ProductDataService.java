package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.ProductData;
import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.repository.ProductRepository;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductDataService {

    private final ProductRepository productRepository;
    private final StoreDataService storeDataService;
    private final ProductStateDataService stateDataService;
    private final SubProductDataService subProductDataService;

    /**
     * Create unique products
     * @param productsForm
     */
    public void create(List<ProductRestResponse> productsForm, String storeName) {

        log.info("Creating products:[{}]", productsForm.size());

        if (!productsForm.isEmpty()) {

            Store store = storeDataService.findByName(storeName);

            String storeUid = store.getUuid().toString();

            List<Product> products = productsForm.stream()
                    .map(product -> Product.builder()
                            .uuid(UUID.randomUUID())
                            .handle(product.getHandle())
                            .stores(List.of(store))
                            .build()).collect(Collectors.toList());

            store.getProducts().addAll(products);

            List<Product> savedProducts = productRepository.saveAll(products);

            savedProducts.forEach(product ->
                    stateDataService.create(storeUid, product, ProductRestResponse.filterByHandle(productsForm, product.getHandle()) ));
        }
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

        product.ifPresent(existProduct -> log.info("...found:[{}]", existProduct.getId()));

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
     * Update product
     * @param productUid
     * @param request
     * @return
     */
    public Product update(String productUid, String storeUid, ProductRestResponse request) {

        log.info("Updating product by uuid:[{}]", productUid);

        Product product = this.findByUuid(productUid);

        product.setHandle(request.getHandle());

        Optional<ProductData> productData = product.getStates().stream().filter(state -> state.getStoreUid().equals(storeUid)).findFirst();

        productData.ifPresent(state -> stateDataService.update(state.getUuid().toString(), request));

        subProductDataService.update(storeUid, product, request);

        productRepository.save(product);

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


    /**
     * Refresh product state
     * @param productUid
     * @param storeUid
     * @param request
     */
    public void refreshState(String productUid, String storeUid, ProductRestResponse request) {

        Product product = this.findByUuid(productUid);

        Optional<ProductData> productData = product.getStates().stream().filter(state -> state.getStoreUid().equals(storeUid)).findFirst();

        productData.ifPresent(productState -> stateDataService.update(productState.getUuid().toString(), request));

        subProductDataService.update(storeUid, product, request);
    }
}

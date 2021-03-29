package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.repository.ProductRepository;
import com.eliftech.shopify.rest.model.Image;
import com.eliftech.shopify.rest.model.Option;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductDataService {

    private final ProductRepository productRepository;
    private final StoreDataService storeDataService;

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
                 .description(Map.of(storeUid, product.getBodyHtml()))
                 .title(Map.of(storeName, product.getTitle()))
                 .tags(product.getTags())
                 .sinceId(product.getId())
                 .images(product.getImages().stream().map(Image::getSrc).collect(Collectors.toList()))
                 .status(product.getStatus())
                 .stores(List.of(store))
                 .build()).collect(Collectors.toList());

        store.getProducts().addAll(products);

        productRepository.saveAll(products);
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
    public List<Product> findAll(String storeUid) {

        log.info("Searching  products by store:[{}]", storeUid);

        List<Product> products = productRepository.findAll(storeUid);

        log.info("...found:[{}]", products.size());

        return products;
    }
}

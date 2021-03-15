package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.repository.ProductRepository;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductDataService {

    private final ProductRepository productRepository;

    /**
     * Create unique products
     * @param productsForm
     */
    public void create(List<ProductRestResponse> productsForm, String storeName) {

        log.info("Creating products:[{}]", productsForm.size());

        List<Product> products = productsForm.stream().map(product -> {

//            Product.builder()
//                    .handle(product) // TODO: map to entities

            return new Product();

        }).collect(Collectors.toList());

        productRepository.saveAll(products);
    }
}

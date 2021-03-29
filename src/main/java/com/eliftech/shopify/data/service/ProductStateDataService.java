package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.ProductData;
import com.eliftech.shopify.data.entity.Store;
import com.eliftech.shopify.data.repository.ProductDataRepository;
import com.eliftech.shopify.rest.model.Image;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductStateDataService {

    private final ProductDataRepository productDataRepository;

    /**
     * Create product state
     * @param storeUid
     * @param product
     * @param request
     * @return
     */
    public ProductData create(String storeUid, Product product, ProductRestResponse request) {

        log.info("Creating state for product:[{}]", product.getUuid());

        ProductData state = ProductData.builder()
                .storeUid(storeUid)
                .product(product)
                .description(request.getBodyHtml())
                .title(request.getTitle())
                .tags(request.getTags())
                .images(request.getImages().stream().map(Image::getSrc).collect(Collectors.toList()))
                .status(request.getStatus())
                .build();

        return productDataRepository.save(state);
    }
}

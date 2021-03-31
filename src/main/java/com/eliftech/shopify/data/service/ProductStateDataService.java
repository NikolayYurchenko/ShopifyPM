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

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductStateDataService {

    private final SubProductDataService subProductDataService;
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
                .uuid(UUID.randomUUID())
                .storeUid(storeUid)
                .product(product)
                .vendor(request.getVendor())
//                .inventory(request.get)
                .productType(request.getProductType())
                .description(request.getBodyHtml())
                .title(request.getTitle())
                .tags(request.getTags())
                .images(request.getImages().stream().map(Image::getSrc).collect(Collectors.toList()))
                .status(request.getStatus())
                .build();

        ProductData savedState = productDataRepository.save(state);

        subProductDataService.create(state, request.getVariants());

        return savedState;
    }

    /**
     * Update product state
     * @param productDataUid
     * @param request
     */
    public void update(String productDataUid, ProductRestResponse request) {

        log.info("Updating product state:[{}] to:[{}]", productDataUid, request);

        ProductData state = productDataRepository.findByUuid(UUID.fromString(productDataUid))
                .orElseThrow(() -> new EntityNotFoundException("Not found product state by uuid:["+ productDataUid +"]"));

        state.setVendor(request.getVendor());

        state.setProductType(request.getProductType());

        state.setDescription(request.getBodyHtml());

        state.setTitle(request.getTitle());

        state.setTags(request.getTags());

        state.setImages(request.getImages().stream().map(Image::getSrc).collect(Collectors.toList()));

        state.setStatus(request.getStatus());

        productDataRepository.save(state);
    }
}

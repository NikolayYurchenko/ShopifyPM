package com.eliftech.shopify.data.service;


import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.SubProduct;
import com.eliftech.shopify.data.repository.SubProductRepository;
import com.eliftech.shopify.rest.model.Variant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SubProductDataService {

    private final SubProductRepository subProductRepository;

    /**
     * Create sub products
     * @param product
     * @param variantForms
     * @return
     */
    protected void create(Product product, List<Variant> variantForms) {

        log.info("Creating sub products for product:[{}], size:[{}]", product.getUuid(), variantForms.size());

        if (!variantForms.isEmpty()) {

            List<SubProduct> subProducts = variantForms.stream().map(variant -> SubProduct.builder()
                    .uuid(UUID.randomUUID())
                    .externalId(variant.getId())
                    .product(product)
                    .title(variant.getTitle())
                    .price(variant.getPrice())
                    .sku(variant.getSku())
                    .position(variant.getPosition())
                    .inventoryPolicy(variant.getInventoryPolicy())
                    .compareAtPrice(variant.getCompareAtPrice())
                    .fulfillmentService(variant.getFulfillmentService())
                    .inventoryManagement(variant.getInventoryManagement())
                    .option1(variant.getOption1())
                    .option2(variant.getOption2())
                    .option3(variant.getOption3())
                    .barcode(variant.getBarcode())
                    .imageId(variant.getImageId())
                    .size(variant.getSize())
                    .color(variant.getColor())
                    .build())
                    .collect(Collectors.toList());

            subProductRepository.saveAll(subProducts);
        }
    }

    /**
     * Find by uuid
     * @param subProductUid
     * @return
     */
    protected SubProduct findByUuid(String subProductUid) {

        log.info("Searching sub product by uuid:[{}]", subProductUid);

        SubProduct subProduct = subProductRepository.findByUuid(UUID.fromString(subProductUid))
                .orElseThrow(() -> new EntityNotFoundException("Not found sub product by uuid:["+ subProductUid +"]"));

        log.info("...found:[{}]", subProduct.getId());

        return subProduct;
    }

    /**
     * Update sub products
     * @param variantFormEntries
     */
    protected void update(Map<String, Variant> variantFormEntries) {

        log.info("Updating sub products by ids:{}", variantFormEntries.keySet());

        List<SubProduct> updateSubProducts = new ArrayList<>();

        variantFormEntries.forEach((subProductUid, variant) -> {

            SubProduct subProduct = this.findByUuid(subProductUid);

            subProduct.setTitle(variant.getTitle());

            subProduct.setPrice(variant.getPrice());

            subProduct.setSku(variant.getSku());

            subProduct.setPosition(variant.getPosition());

            subProduct.setInventoryPolicy(variant.getInventoryPolicy());

            subProduct.setCompareAtPrice(variant.getCompareAtPrice());

            subProduct.setFulfillmentService(variant.getFulfillmentService());

            subProduct.setInventoryManagement(variant.getInventoryManagement());

            subProduct.setOption1(variant.getOption1());

            subProduct.setOption2(variant.getOption2());

            subProduct.setOption3(variant.getOption3());

            subProduct.setBarcode(variant.getBarcode());

            subProduct.setImageId(variant.getImageId());

            subProduct.setSize(variant.getSize());

            subProduct.setColor(variant.getColor());

            updateSubProducts.add(subProduct);
        });

        subProductRepository.saveAll(updateSubProducts);
    }
}

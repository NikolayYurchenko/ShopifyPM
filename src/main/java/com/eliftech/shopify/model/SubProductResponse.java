package com.eliftech.shopify.model;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.SubProduct;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubProductResponse {

    private String uuid;

    private String externalId;

    private String title;

    private String price;

    private String sku;

    private String position;

    private String inventoryPolicy;

    private String compareAtPrice;

    private String fulfillmentService;

    private String inventoryManagement;

    private String option1;

    private String option2;

    private String option3;

    private String barcode;

    private String imageId;

    private Double weight;

    private String size;

    private String color;

    public static SubProductResponse instance(SubProduct subProduct) {

        return SubProductResponse.builder()
                .uuid(subProduct.getUuid().toString())
                .externalId(subProduct.getExternalId())
                .title(subProduct.getTitle())
                .price(subProduct.getPrice())
                .sku(subProduct.getSku())
                .position(subProduct.getPosition())
                .inventoryPolicy(subProduct.getInventoryPolicy())
                .compareAtPrice(subProduct.getCompareAtPrice())
                .fulfillmentService(subProduct.getFulfillmentService())
                .inventoryManagement(subProduct.getInventoryManagement())
                .option1(subProduct.getOption1())
                .option2(subProduct.getOption2())
                .option3(subProduct.getOption3())
                .barcode(subProduct.getBarcode())
                .imageId(subProduct.getImageId())
                .size(subProduct.getSize())
                .color(subProduct.getColor())
                .build();
    }

    public static List<SubProductResponse> instance(List<SubProduct> subProducts) {

        return subProducts.stream()
                .map(SubProductResponse::instance)
                .collect(Collectors.toList());
    }
}

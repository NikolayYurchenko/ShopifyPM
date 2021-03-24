package com.eliftech.shopify.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variant {

    private String id;

    private String productId;

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

    private String createdAt;

    private String updatedAt;

    private Boolean taxable;

    private String barcode;

    private String grams;

    private String imageId;

    private Double weight;

    private String weightUnit;

    private String inventoryItemId;

    private Boolean requiresShipping;

    private String adminGraphqlApiId;

    private String size;

    private String color;

    private String appUniqueKey;

    public String getKey() {

        return StringUtils.joinWith("-", StringUtils
                .trim(this.getOption1()),
                StringUtils.trim(this.getOption2()),StringUtils.trim(this.getOption3()));
    }
}

package com.eliftech.shopify.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemResponse {

    private String id;

    private String sku;

    private String createdAt;

    private String updatedAt;

    private String requiresShipping;

    private String cost;

    private String countryCodeOfOrigin;

    private String provinceCodeOfOrigin;

    private String harmonizedSystemCode;

    private String tracked;

    private String countryHarmonizedSystemCodes;

    private String adminGraphqlApiId;
}

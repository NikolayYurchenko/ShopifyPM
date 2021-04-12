package com.eliftech.shopify.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantUpdateRequest  {

    @NotBlank
    private String id;

    @NotBlank
    private String size;

    @NotBlank
    private String title;

    @NotBlank
    private String option1;

    @NotBlank
    private String option2;

    @NotBlank
    private String option3;

    @NotBlank
    private String price;

    @NotBlank
    private String inventoryManagement;

    @NotBlank
    private String inventoryPolicy;

    @NotBlank
    private String sku;

    @NotBlank
    private String barCode;
}

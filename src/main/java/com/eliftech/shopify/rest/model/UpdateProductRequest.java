package com.eliftech.shopify.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String bodyHtml;

    @NotBlank
    private String vendor;

    @NotBlank
    private String productType;

    @NotBlank
    private String handle;

    @NotBlank
    private String tags;

//    private List<Image> images = Collections.emptyList();

    @NotBlank
    private String status;

    private List<AbstractItem> variants = Collections.emptyList();
}

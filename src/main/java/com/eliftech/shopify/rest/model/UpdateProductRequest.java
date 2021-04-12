package com.eliftech.shopify.rest.model;

import com.eliftech.shopify.model.ImageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty
    private List<ImageRequest> images = Collections.emptyList();

    @NotBlank
    private String status;

    @NotEmpty
    private List<VariantUpdateRequest> variants = Collections.emptyList();
}

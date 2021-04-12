package com.eliftech.shopify.model;

import com.eliftech.shopify.rest.model.VariantUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateForm {

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

    @NotBlank
    private String status;

    @NotEmpty
    private List<VariantUpdateRequest> variants;
}

package com.eliftech.shopify.model;

import com.eliftech.shopify.rest.model.VariantUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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

//    private List<Image> images = Collections.emptyList();

    @NotBlank
    private String status;

    private List<VariantUpdateRequest> variants = new ArrayList<>();

    public List<String> getVariantIds() {

        return this.variants.stream()
                .map(VariantUpdateRequest::getId)
                .collect(Collectors.toList());
    }
}

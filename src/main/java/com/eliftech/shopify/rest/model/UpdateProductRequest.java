package com.eliftech.shopify.rest.model;

import com.eliftech.shopify.model.ProductUpdateForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    private String title;

    private String bodyHtml;

    private String vendor;

    private String productType;

    private String handle;

    private String tags;

//    private List<Image> images = Collections.emptyList();

    private String status;

    @Builder.Default
    private List<Object> variants = new ArrayList<>();

    public static UpdateProductRequest instance(ProductUpdateForm request) {

        return UpdateProductRequest.builder()
                .title(request.getTitle())
                .bodyHtml(request.getBodyHtml())
                .vendor(request.getVendor())
                .productType(request.getProductType())
                .handle(request.getHandle())
                .tags(request.getTags())
                .status(request.getStatus())
                .variants(new ArrayList<>())
                .build();
    }
}

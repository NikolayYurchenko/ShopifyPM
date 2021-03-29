package com.eliftech.shopify.model;

import com.eliftech.shopify.data.entity.Product;
import com.eliftech.shopify.data.entity.ProductData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String storeUid;

    private String id;

    private String title;

    private String bodyHtml;

    private String handle;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String tags;

    public static ProductResponse instance(String storeUid, ProductData product) {

        return ProductResponse.builder()
                .storeUid(storeUid)
                .id(product.getProduct().getSinceId())
                .title(product.getTitle())
                .bodyHtml(product.getDescription())
                .handle(product.getProduct().getHandle())
                .updatedAt(product.getProduct().getUpdatedAt())
                .createdAt(product.getProduct().getCreatedAt())
                .tags(product.getTags())
                .build();
    }

    public static List<ProductResponse> instance(String storeUid, List<ProductData> products) {

        return products.stream()
                .map(p -> ProductResponse.instance(storeUid, p))
                .collect(Collectors.toList());
    }
}

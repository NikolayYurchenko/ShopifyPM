package com.eliftech.shopify.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    private String id;

    private String productId;

    private String position;

    private String createdAt;

    private String updatedAt;

    private Object alt;

    private String width;

    private String height;

    private String src;

    private List<String> variantIds = null;

    private String adminGraphqlApiId;
}

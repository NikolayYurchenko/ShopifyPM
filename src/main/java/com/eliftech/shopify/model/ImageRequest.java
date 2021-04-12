package com.eliftech.shopify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String src;
}

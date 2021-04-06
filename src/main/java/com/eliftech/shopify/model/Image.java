package com.eliftech.shopify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    private String id;

    private String src;

    public static Image instance(com.eliftech.shopify.rest.model.Image restImage) {

        return new Image(restImage.getId(), restImage.getSrc());
    }
}

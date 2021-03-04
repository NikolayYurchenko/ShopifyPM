package com.eliftech.shopify.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRestForm {

    private String id;

    private String title;

    private String bodyHtml;

    private String vendor;

    private String productType;

    private String createdAt;

    private String handle;

    private String updatedAt;

    private String publishedAt;

    private Object templateSuffix;

    private String publishedScope;

    private String tags;

    private String adminGraphqlApiId;

//    public List<Variant> variants = null;
//
//    public List<Option> options = null;
//
//    public List<Image> images = null;
//
//    public Image image;


    private String status;

    private int colorIndex;

    private int sizeIndex;

    private String handleFixed;
}

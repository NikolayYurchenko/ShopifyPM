package com.eliftech.shopify.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

     private String id;

     private String variantId;

     private String title;

     private String quantity;

     private String sku;

     private String vendor;

     private String  productId;
}

package com.eliftech.shopify.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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

     private List<OrderItemProperty> properties;

     public static OrderItem filterByExternalId(List<OrderItem> orderItems, String externalUid) {

          return orderItems.stream().filter(item -> item.getId().equals(externalUid))
                  .findFirst()
                  .orElseThrow(() -> new EntityNotFoundException("Fail to filter, sub product by external uuid:["+ externalUid +"] not found"));
     }
}

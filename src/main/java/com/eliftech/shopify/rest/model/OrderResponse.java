package com.eliftech.shopify.rest.model;

import com.eliftech.shopify.model.FactoryType;
import com.eliftech.shopify.util.NumberUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

     private String id;

     private String email;

     private String closedAt;

     private String createdAt;

     private String updatedAt;

     private int number;

     private String note;

     private String totalPrice;

     private String subtotalPrice;

     private String totalWeight;

     private String totalTax;

     private boolean taxesIncluded;

     private String currency;

     private String financialStatus;

     private boolean confirmed;

     private String totalDiscounts;

     private String totalLineItemsPrice;

     private String name;

     private String reference;

     private String userId;

     private String phone;

     private Address shippingAddress;

     private List<OrderItem> lineItems;

     public FactoryType defineFactoryBySku(String sku) {

          String number = NumberUtil.isNumeric(sku.substring(sku.indexOf("-") + 1))
                  ? sku.substring(sku.indexOf("-") + 1) : "1";

          return switch(number) {

               case "1" ->  FactoryType.FIRST;
               case "2" ->  FactoryType.SECOND;
               case "3" ->  FactoryType.THIRD;
               case "4" ->  FactoryType.FOURTH;
               case "5" ->  FactoryType.FIFTH;
               default -> throw new IllegalStateException("Unexpected number parsed from sku: " + number);
          };
     }
}

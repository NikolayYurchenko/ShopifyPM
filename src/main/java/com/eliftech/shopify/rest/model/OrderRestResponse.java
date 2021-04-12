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
public class OrderRestResponse {

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
          
          FactoryType type;
          
          switch(number) {
               
               case "1":
                    type = FactoryType.FIRST;

               case "2":
                    type = FactoryType.SECOND;

               case "3":
                    type = FactoryType.THIRD;

               case "4":
                    type = FactoryType.FOURTH;

               case "5":
                    type = FactoryType.FIFTH;
                    break;
               default:
                    throw new IllegalStateException("Unexpected value: " + number);
          }

          return type;
     }
}

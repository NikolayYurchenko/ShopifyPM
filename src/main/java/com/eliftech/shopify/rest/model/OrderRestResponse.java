package com.eliftech.shopify.rest.model;

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
}

package com.eliftech.shopify.model;

import com.eliftech.shopify.data.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private String storeUid;

    private String externalId;

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

    public static OrderResponse instance(Order order) {

        return OrderResponse.builder()
                .storeUid(order.getStoreUid())
                .closedAt(order.getClosedAt())
                .confirmed(order.isConfirmed())
                .createdAt(order.getCreatedAt())
                .currency(order.getCurrency())
                .email(order.getEmail())
                .externalId(order.getExternalId())
                .financialStatus(order.getFinancialStatus())
                .name(order.getName())
                .note(order.getNote())
                .number(order.getNumber())
                .phone(order.getPhone())
                .reference(order.getReference())
                .subtotalPrice(order.getSubtotalPrice())
                .taxesIncluded(order.isTaxesIncluded())
                .totalDiscounts(order.getTotalDiscounts())
                .totalLineItemsPrice(order.getTotalLineItemsPrice())
                .totalPrice(order.getTotalPrice())
                .totalTax(order.getTotalTax())
                .totalWeight(order.getTotalWeight())
                .updatedAt(order.getUpdatedAt())
                .userId(order.getUserId())
                .build();
    }

    public static List<OrderResponse> instance(List<Order> orders) {

        return orders.stream()
                .map(OrderResponse::instance)
                .collect(Collectors.toList());
    }
}

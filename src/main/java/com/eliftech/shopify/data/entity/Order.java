package com.eliftech.shopify.data.entity;


import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "store_uid")
    private String storeUid;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "email")
    private String email;

    @Column(name = "closed_at")
    private String closedAt;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "number")
    private int number;

    @Column(name = "note")
    private String note;

    @Column(name = "total_price")
    private String totalPrice;

    @Column(name = "sub_total_price")
    private String subtotalPrice;

    @Column(name = "total_weight")
    private String totalWeight;

    @Column(name = "total_tax")
    private String totalTax;

    @Column(name = "taxes_included")
    private boolean taxesIncluded;

    @Column(name = "currency")
    private String currency;

    @Column(name = "financial_status")
    private String financialStatus;

    @Column(name = "confirmed")
    private boolean confirmed;

    @Column(name = "total_discounts")
    private String totalDiscounts;

    @Column(name = "total_line_items_price")
    private String totalLineItemsPrice;

    @Column(name = "name")
    private String name;

    @Column(name = "reference")
    private String reference;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "phone")
    private String phone;
}

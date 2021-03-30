package com.eliftech.shopify.data.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sub_products")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class SubProduct extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private String price;

    @Column(name = "sku")
    private String sku;

    @Column(name = "position")
    private String position;

    @Column(name = "inventory_policy")
    private String inventoryPolicy;

    @Column(name = "compare_at_price")
    private String compareAtPrice;

    @Column(name = "fulfillment_service")
    private String fulfillmentService;

    @Column(name = "inventory_management")
    private String inventoryManagement;

    @Column(name = "option1")
    private String option1;

    @Column(name = "option2")
    private String option2;

    @Column(name = "option3")
    private String option3;

    @Column(name = "bar_code")
    private String barcode;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;
}

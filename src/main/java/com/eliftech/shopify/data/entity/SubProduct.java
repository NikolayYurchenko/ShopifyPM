package com.eliftech.shopify.data.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

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

    @Type(type = "uuid-char")
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(36) default ''")
    private UUID uuid;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_data_id")
    private ProductData product;

    @Column(name = "product_id")
    private String productUid;

    @Column(name = "external_id")
    private String externalId;

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

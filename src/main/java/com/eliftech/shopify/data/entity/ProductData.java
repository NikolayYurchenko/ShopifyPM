package com.eliftech.shopify.data.entity;

import com.eliftech.shopify.model.Image;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products_data")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class ProductData extends BaseEntity {

    @Type(type = "uuid-char")
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(36) default ''")
    private UUID uuid;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<SubProduct> subProducts = Collections.emptyList();

    @Column(name = "store_uuid")
    private String storeUid;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "varchar(5000)")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "tags")
    private String tags;

    @Column(name = "status")
    private String status;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "inventory")
    private String inventory;

    @Column(name = "product_type")
    private String productType;

    @Type( type = "json" )
    @Column(name = "images", columnDefinition = "json")
    private List<Image> images;
}

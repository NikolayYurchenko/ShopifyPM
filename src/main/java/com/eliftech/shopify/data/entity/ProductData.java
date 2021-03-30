package com.eliftech.shopify.data.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

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

    @Type( type = "json" )
    @Column(name = "images", columnDefinition = "json")
    private List<String> images;
}

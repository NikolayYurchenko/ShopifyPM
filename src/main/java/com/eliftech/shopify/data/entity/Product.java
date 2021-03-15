package com.eliftech.shopify.data.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products", indexes = {@Index(name = "product_uuid_index", columnList = "uuid")})
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Product extends  BaseEntity {

    @Type(type = "uuid-char")
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(36) default ''")
    private UUID uuid;

    @Column(name = "since_id")
    public String sinceId;

    @Column(name = "handle", nullable = false)
    public String handle;

    @Type( type = "json" )
    @Column(name = "title", columnDefinition = "json")
    public Map<String, String> title;

    @Type( type = "json" )
    @Column(name = "description", columnDefinition = "json")
    public Map<String, String> description;

    @Column(name = "price")
    public String price;

    @Column(name = "tags")
    public String tags;

    @Column(name = "status")
    public String status;

    @Column(name = "images")
    public String images;
}

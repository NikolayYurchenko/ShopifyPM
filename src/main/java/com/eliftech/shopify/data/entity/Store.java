package com.eliftech.shopify.data.entity;

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
@Table(name = "stores", indexes = {@Index(name = "store_uuid_index", columnList = "uuid")})
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Store extends BaseEntity {

    @Type(type = "uuid-char")
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(36) default ''")
    private UUID uuid;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "api_key", nullable = false)
    public String apiKey;

    @ToString.Exclude
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "store_product",
            joinColumns = @JoinColumn(name = "store_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<Product> products = Collections.emptyList();
}

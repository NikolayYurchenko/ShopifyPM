package com.eliftech.shopify.data.entity;

import com.eliftech.shopify.model.FactoryType;
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
@Table(name = "table_configurations")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class TableConfiguration extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "factory_type")
    private FactoryType type;

    @Column(name = "table_uuid")
    private String tableUid;
}

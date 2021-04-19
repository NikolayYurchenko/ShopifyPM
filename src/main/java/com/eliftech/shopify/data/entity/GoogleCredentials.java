package com.eliftech.shopify.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "google_credentials")
@EntityListeners(AuditingEntityListener.class)
public class GoogleCredentials extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "api_key")
    private String apiKey;
}

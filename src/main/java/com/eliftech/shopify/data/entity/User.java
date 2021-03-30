package com.eliftech.shopify.data.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "login_session_key")
    private String loginSessionKey;

    @Column(name = "email_status")
    private String emailStatus;

    @Column(name = "password_expire_data")
    private LocalDateTime passwordExpireData;

    @Column(name = "password_reset_key")
    private String passwordResetKey;
}

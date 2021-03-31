package com.eliftech.shopify.data.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email_address")
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

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

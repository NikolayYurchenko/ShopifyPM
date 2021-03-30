package com.eliftech.shopify.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

    private String name;

    private String password;

    private String email;

    private String role;

    private String loginSessionKey;

    private String emailStatus;

    private LocalDateTime passwordExpireData;

    private String passwordResetKey;
}

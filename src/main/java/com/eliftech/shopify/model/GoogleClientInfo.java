package com.eliftech.shopify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleClientInfo {

    @NotBlank
    private String accessToken;

    @NotNull
    private Long expiresInSeconds;
}

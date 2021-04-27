package com.eliftech.shopify.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractApiResponse {

    private String status;

    private Integer code;
}

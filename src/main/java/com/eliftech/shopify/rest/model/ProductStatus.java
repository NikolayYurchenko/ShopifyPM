package com.eliftech.shopify.rest.model;

public enum ProductStatus {

    ACTIVE("active"),
    DRAFT("draft"),
    ARCHIVE("archive");

    private final String statusName;

    ProductStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}

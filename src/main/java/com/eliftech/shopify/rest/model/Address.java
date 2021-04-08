package com.eliftech.shopify.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String address1;

    private String firstName;

    private String lastName;

    private String phone;

    private String city;

    private String name;
}

package com.eliftech.shopify.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SuccessApiResponse extends AbstractApiResponse {

    private String message;

    public static SuccessApiResponse instance() {

        SuccessApiResponse response = new SuccessApiResponse("Orders success sync");

        response.setStatus(HttpStatus.OK.toString());
        response.setCode(200);

        return response;
    }
 }

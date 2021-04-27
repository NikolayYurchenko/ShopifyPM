package com.eliftech.shopify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ForbiddenApiResponse extends AbstractApiResponse {

    private String message;

    public static ForbiddenApiResponse instance() {

        ForbiddenApiResponse response = new ForbiddenApiResponse("Orders sync error");

        response.setStatus(HttpStatus.FORBIDDEN.toString());
        response.setCode(403);

        return response;
    }
}

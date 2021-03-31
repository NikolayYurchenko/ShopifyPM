package com.eliftech.shopify.exception;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResource {
    String msg;
    String errorMsg;
    String code;
}

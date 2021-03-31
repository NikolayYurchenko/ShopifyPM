package com.eliftech.shopify.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Data
public class ApiErrorResponse {

    int code;
    Long timestamp;
    String message;
    String cause;
    Map<String, String> errors;

    public ApiErrorResponse() {
        timestamp = System.currentTimeMillis();
        code = 500;
    }

    public ApiErrorResponse(ConstraintViolationException e) {
        code = HttpStatus.UNPROCESSABLE_ENTITY.value();
        timestamp = System.currentTimeMillis();
        message = e.getMessage();
        cause = e.toString();
        errors = new HashMap<>();
        e.getConstraintViolations().forEach(it -> {
            errors.put(it.getPropertyPath().toString(), it.getMessage());
        });
    }

    public ApiErrorResponse(String causeMessage) {
        this.timestamp = System.currentTimeMillis();
        this.cause = causeMessage;
        this.code = 500;
    }

    public ApiErrorResponse(Exception e) {

        String message = e.getMessage();

        code = 500;

        timestamp = System.currentTimeMillis();
        cause = message;
    }

    public ApiErrorResponse(Exception e, HttpStatus status) {
        timestamp = System.currentTimeMillis();
        cause = e.getMessage();
        if (status != null) {
            code = status.value();
        } else {
            code = 500;
        }
    }
}

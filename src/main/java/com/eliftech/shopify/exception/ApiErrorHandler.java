package com.eliftech.shopify.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
class ApiErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ApiErrorResponse exceptionConstraintHandler(ConstraintViolationException e) {
        log.error("Error model validation", e);
        return new ApiErrorResponse(e);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> exceptionHandler(Exception e) {

        String message = e.getMessage();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiErrorResponse errorResponse = new ApiErrorResponse(e.getMessage());

        message = e.getMessage();

        if (e instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = e.getMessage();
            errorResponse.setCode(status.value());
        }

        log.error("Internal server error. Cause:[" + message + "]", e);

        return new ResponseEntity<>(errorResponse, status);
    }
}
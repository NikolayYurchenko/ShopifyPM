package com.eliftech.shopify.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ErrorResource handleNullPointerException(Exception e) {
        ErrorResource errorResource = new ErrorResource();
        errorResource.setCode("404");
        errorResource.setMsg("Page not Found");
        errorResource.setErrorMsg(e.getMessage());
        log.error("A null pointer exception occurred ", e);
        return errorResource;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorResource handleAllException(Exception e) {
        ErrorResource errorResource = new ErrorResource();
        errorResource.setCode("500");
        errorResource.setMsg("A unknown Exception Occurred");
        errorResource.setErrorMsg(e.getMessage());
        log.error("A unknown Exception Occurred ", e);
        return errorResource;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleResourceNotFoundException() {

        ErrorResource errorResource = new ErrorResource();
        errorResource.setCode("500");
        errorResource.setMsg("A unknown Exception Occurred");
        log.error("A unknown Exception Occurred ");
        return "notFoundJSPPage";
    }

}

package com.eliftech.shopify.controller;

import com.eliftech.shopify.amqp.publisher.AmqpPublisher;
import com.eliftech.shopify.service.contract.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@Api(tags = "Product API")
@RequestMapping("/api/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final AmqpPublisher amqpPublisher;

    @PostMapping("/sync")
    @ApiOperation("Sync products")
    public void sync(@RequestParam @NotBlank String storeName) {

        log.info("Request for sync products by store name:[{}]", storeName);

        amqpPublisher.notifyProductSync(storeName);
    }
}

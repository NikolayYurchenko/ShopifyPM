package com.eliftech.shopify.controller;

import com.eliftech.shopify.model.AbstractApiResponse;
import com.eliftech.shopify.model.GoogleClientInfo;
import com.eliftech.shopify.model.OrderResponse;
import com.eliftech.shopify.service.contract.GoogleApp;
import com.eliftech.shopify.service.contract.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "Orders API")
@RequestMapping("/api/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;
    private final GoogleApp googleApp;


    @PostMapping("/sheets/authorize")
    @ApiOperation("Authorize for sheets")
    public void authorizeForSheets(@RequestBody @Valid GoogleClientInfo clientInfo) {

        log.info("Request for authorize for sheets");

        googleApp.authorize(clientInfo);
    }

    @PostMapping("/sync")
    @ApiOperation("Sync orders")
    public AbstractApiResponse sync(@RequestParam @NotBlank String storeName) {

        log.info("Request for sync orders by store name:[{}]", storeName);

        return orderService.sync(storeName);
    }

    @GetMapping
    @ApiOperation("Find all orders")
    public List<OrderResponse> findAll(@RequestParam @NotBlank String storeName,
                                       @RequestParam @Min(0L) @Max(100L) int page,
                                       @RequestParam @Min(1L) @Max(100L) int limit) {

        log.info("Request for get orders by store name:[{}], ({}, {})", storeName, page, limit);

        return orderService.findAll(storeName, page, limit);
    }
}

package com.eliftech.shopify.rest.model;

import com.eliftech.shopify.util.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemProperty {

    private String name;

    private String value;

    public static List<String> formatForSheets(List<OrderItemProperty> properties) {

        return properties.stream()
                .map(OrderItemProperty::getValue)
                .filter(propertyValue -> !OrderUtil.isUrlPresentInPropertyValue(propertyValue))
                .collect(Collectors.toList());
    }
}

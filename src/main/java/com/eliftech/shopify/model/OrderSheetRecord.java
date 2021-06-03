package com.eliftech.shopify.model;

import com.eliftech.shopify.data.entity.SubProduct;
import com.eliftech.shopify.rest.model.OrderItem;
import com.eliftech.shopify.rest.model.OrderItemProperty;
import com.eliftech.shopify.rest.model.OrderRestResponse;
import com.eliftech.shopify.service.OrderDictionary;
import com.eliftech.shopify.util.OptionalUtil;
import com.eliftech.shopify.util.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSheetRecord {

    private FactoryType factoryType;

    @Builder.Default
    private String externalId = "";

    @Builder.Default
    private String orderNumber = "";

    @Builder.Default
    private String lot = "";

    @Builder.Default
    private String entryDate = "";

    @Builder.Default
    private String styleAndSize = "";

    @Builder.Default
    private String ldStyle = "";

    @Builder.Default
    private String sku = "";

    @Builder.Default
    private String color = "";

    @Builder.Default
    private String barCode = "";

    @Builder.Default
    private String childName = "";

    @Builder.Default
    private String customerName = "";

    @Builder.Default
    private String quantity = "";

    @Builder.Default
    private String quantitySend = "";

    @Builder.Default
    private String estimatedDelivery = "";

    @Builder.Default
    private String sendDate = "";

    @Builder.Default
    private String invoiceNumber = "";

    @Builder.Default
    private String address = "";

    @Builder.Default
    private String notes = "";

    @Builder.Default
    private String remark = "";

    @Builder.Default
    private String properties = "";

    @Builder.Default
    private String customerEmail = "";


    public static OrderSheetRecord instance(OrderRestResponse order, SubProduct subProduct, String sku, FactoryType factoryType, boolean addLetter) {

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return OrderSheetRecord.builder()
                .factoryType(factoryType)
                .externalId(order.getId())
                .barCode(OptionalUtil.getStringOrEmpty(subProduct.getBarcode()))
                .orderNumber(addLetter? order.getNumber() + OrderDictionary.getLetterDependOnFactoryType(factoryType) : String.valueOf(order.getNumber()))
                .entryDate(OffsetDateTime.parse(order.getCreatedAt()).format(formatters))
                .estimatedDelivery(OffsetDateTime.parse(order.getCreatedAt()).plusDays(12).format(formatters))
                .styleAndSize(OptionalUtil.getStringOrEmpty(OrderUtil.getStyleAndSizeFromBaseInfo(subProduct.getTitle())))
                .sku(sku)
                .childName(OptionalUtil.getStringOrEmpty(subProduct.getTitle()))
                .customerName(order.getShippingAddress() == null ? "" : OptionalUtil.formatName(order.getShippingAddress().getFirstName(), order.getShippingAddress().getLastName()))
                .address(order.getShippingAddress() == null ? "" : order.getShippingAddress().getCountry() + ":" + order.getShippingAddress().getCity() + ":" + order.getShippingAddress().getAddress1())
                .quantity(String.valueOf(order.getLineItems().size()))
                .color(OptionalUtil.getStringOrEmpty(OrderUtil.getColorFromBaseInfo(subProduct.getTitle())))
                .notes(OptionalUtil.getStringOrEmpty(order.getNote()))
                .customerEmail(order.getEmail())
                .properties(String.join(",", OrderItemProperty
                        .formatForSheets(OrderItem.filterByExternalId(order.getLineItems(), subProduct.getExternalId()).getProperties())))
                .build();
    }

    public List<List<Object>> prettifyForSheets() {

       return List.of(

                List.of(this.orderNumber, this.lot, this.entryDate,
                        this.styleAndSize, this.ldStyle, this.sku,
                        this.barCode, this.color, this.properties, this.customerName,
                        this.quantity, this.quantitySend, this.estimatedDelivery,
                        this.sendDate, this.invoiceNumber, this.address,
                        this.notes, this.remark, this.customerEmail)
        );
    }
}

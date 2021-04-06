package com.eliftech.shopify.model;

import com.eliftech.shopify.data.entity.SubProduct;
import com.eliftech.shopify.rest.model.OrderResponse;
import com.eliftech.shopify.service.OrderDictionary;
import com.eliftech.shopify.util.OptionalUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSheetRecord {

    private FactoryType factoryType;

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


    public static OrderSheetRecord instance(OrderResponse order, SubProduct subProduct, String sku, FactoryType factoryType) {

        return OrderSheetRecord.builder()
                .factoryType(factoryType)
                .barCode(OptionalUtil.getStringOrEmpty(subProduct.getBarcode()))
                .orderNumber(order.getName() + OrderDictionary.getLetterDependOnFactoryType(factoryType))
                .entryDate(order.getCreatedAt())
                .styleAndSize(OptionalUtil.getConcatenatedOrEmpty(subProduct.getSize(), String.valueOf(subProduct.getWeight())))
                .sku(sku)
                .childName(OptionalUtil.getStringOrEmpty(subProduct.getTitle()))
                .customerName(order.getShippingAddress().getName())
                .address(order.getShippingAddress().getCity() + ":" + order.getShippingAddress().getAddress1())
                .quantity(String.valueOf(order.getLineItems().size()))
                .color(OptionalUtil.getStringOrEmpty(subProduct.getColor()))
                .notes(OptionalUtil.getStringOrEmpty(order.getNote()))
                .build();
    }

    public List<List<Object>> prettifyForSheets() {

       return List.of(

                List.of(this.orderNumber, this.lot, this.entryDate,
                        this.styleAndSize, this.ldStyle, this.sku,
                        this.barCode, this.color, this.childName, this.customerName,
                        this.quantity, this.quantitySend, this.estimatedDelivery,
                        this.sendDate, this.invoiceNumber,
                        this.address, this.notes, this.remark)
        );
    }
}

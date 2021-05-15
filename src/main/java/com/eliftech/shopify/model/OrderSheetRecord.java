package com.eliftech.shopify.model;

import com.eliftech.shopify.data.entity.SubProduct;
import com.eliftech.shopify.rest.model.OrderRestResponse;
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


    public static OrderSheetRecord instance(OrderRestResponse order, SubProduct subProduct, String sku, FactoryType factoryType, boolean addLetter) {

        return OrderSheetRecord.builder()
                .factoryType(factoryType)
                .externalId(order.getId())
                .barCode(OptionalUtil.getStringOrEmpty(subProduct.getBarcode()))
                .orderNumber(addLetter? order.getId() + OrderDictionary.getLetterDependOnFactoryType(factoryType) : order.getId())
                .entryDate(order.getCreatedAt())
                .styleAndSize(OptionalUtil.getConcatenatedOrEmpty(subProduct.getSize(), String.valueOf(subProduct.getWeight())))
                .sku(sku)
                .childName(OptionalUtil.getStringOrEmpty(subProduct.getTitle()))
                .customerName(OptionalUtil.formatName(order.getShippingAddress().getFirstName(), order.getShippingAddress().getLastName()))
                .address(order.getShippingAddress().getCountry() + ":" + order.getShippingAddress().getCity() + ":" + order.getShippingAddress().getAddress1())
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
                        this.sendDate, this.invoiceNumber, this.address,
                        this.notes, this.remark)
        );
    }
}

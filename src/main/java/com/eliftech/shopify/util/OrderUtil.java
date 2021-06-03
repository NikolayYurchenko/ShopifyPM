package com.eliftech.shopify.util;

import com.eliftech.shopify.model.FactoryType;

import java.util.List;

public class OrderUtil {

    public static FactoryType defineFactoryBySku(String sku) {

        String number = NumberUtil.isNumeric(sku.substring(sku.lastIndexOf("-") + 1))
                ? sku.substring(sku.lastIndexOf("-") + 1) : "1";

        FactoryType type;

        switch(number) {

            case "1":
                type = FactoryType.FIRST;
            break;

            case "2":
                type = FactoryType.SECOND;
            break;

            case "3":
                type = FactoryType.THIRD;
            break;

            case "4":
                type = FactoryType.FOURTH;
            break;

            case "5":
                type = FactoryType.FIFTH;
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }

        return type;
    }

    public static List<String> getStaticCountries() {

        return List.of("New Zealand", "Australia");
    }

    public static String getColorFromBaseInfo(String baseInfo) {

        if (baseInfo.contains("/")) {

            String beforeSlashValue = baseInfo.substring(0, baseInfo.indexOf("/"));

            String afterSlashValue = baseInfo.substring(baseInfo.indexOf("/") + 1);

            return beforeSlashValue.contains("Base")? beforeSlashValue : afterSlashValue.contains("Base") ? afterSlashValue: "";

        } else {

            return baseInfo;
        }
    }

    public static String getStyleAndSizeFromBaseInfo(String baseInfo) {

        if (baseInfo.contains("/")) {

            String beforeSlashValue = baseInfo.substring(0, baseInfo.indexOf("/"));

            String afterSlashValue = baseInfo.substring(baseInfo.indexOf("/") + 1);

            if (!beforeSlashValue.contains("Base") && !afterSlashValue.contains("Base")) {

                return baseInfo;

            } else {
                return beforeSlashValue.contains("Base") ? afterSlashValue : beforeSlashValue;
            }

        } else {

            return "";
        }
    }
}

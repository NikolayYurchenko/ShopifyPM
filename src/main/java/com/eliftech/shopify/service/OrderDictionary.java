package com.eliftech.shopify.service;

import com.eliftech.shopify.model.FactoryType;

import java.util.Map;

public class OrderDictionary {

    private static final Map<FactoryType, String> factoryDictionary = Map.of(FactoryType.FIRST, "a",
                                                                      FactoryType.SECOND, "b",
                                                                      FactoryType.THIRD, "c",
                                                                      FactoryType.FOURTH, "d",
                                                                      FactoryType.FIFTH, "e");

    /**
     * Get letter for related factory type
     * @return
     */
    public static String getLetterDependOnFactoryType(FactoryType type) {

        return factoryDictionary.get(type);
    }
}

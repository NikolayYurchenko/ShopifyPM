package com.eliftech.shopify.init.util;

import com.eliftech.shopify.model.FactoryType;
import com.eliftech.shopify.model.StoreForm;
import com.eliftech.shopify.model.UserForm;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticModelBuilder {

    public static List<StoreForm> getStores() {

        return List.of(
                StoreForm.builder()
                        .name("test-eliftech-store")
                        .apiKey("315dfe3e8902c6967b0baa49e701c621")
                        .password("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")
                        .build(),
                StoreForm.builder()
                        .name("test-eliftech-store-2")
                        .apiKey("46b709ba01300903f23d59b0f0e6164f")
                        .password("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")
                        .build()
        );
    }

    public static UserForm getUser() {

        return UserForm.builder()
                .email("email@gmail.com")
                .emailStatus("confirm")
                .loginSessionKey("login-session-key")
                .name("manager")
                .password("manager")
                .passwordExpireData(LocalDateTime.now())
                .passwordResetKey("reset-key")
                .role("admin")
                .build();
    }

    public static Map<FactoryType, String> getTableConfig() {

        Map<FactoryType, String> tableConfigEntries = new HashMap<>();

        tableConfigEntries.put(FactoryType.FIRST, "17RbMUwGAxX1Scdlbhjjmb3lnDOzdUJYYLvQpY76rSp0");
        tableConfigEntries.put(FactoryType.SECOND, "17RbMUwGAxX1Scdlbhjjmb3lnDOzdUJYYLvQpY76rSp0");
        tableConfigEntries.put(FactoryType.THIRD, "17RbMUwGAxX1Scdlbhjjmb3lnDOzdUJYYLvQpY76rSp0");
        tableConfigEntries.put(FactoryType.FOURTH, "17RbMUwGAxX1Scdlbhjjmb3lnDOzdUJYYLvQpY76rSp0");
        tableConfigEntries.put(FactoryType.FIFTH, "17RbMUwGAxX1Scdlbhjjmb3lnDOzdUJYYLvQpY76rSp0");

        return tableConfigEntries;
    }
}

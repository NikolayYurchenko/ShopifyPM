package com.eliftech.shopify.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("shopify.sheets")
public class SheetsStorageProperties {

    private String inputOption;

    private String insertDataOption;

    private String sheetName;

    private String accessType;
}

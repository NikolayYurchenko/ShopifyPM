package com.eliftech.shopify.service.contract;

import com.eliftech.shopify.model.OrderSheetRecord;
import com.google.api.client.auth.oauth2.Credential;

import java.util.List;

public interface GoogleApp {

    /**
     * Authorize in google app
     * @param resourcePath
     * @return
     */
    Credential authorize(String resourcePath);

    /**
     * Execute action(e.g add data to google table)
     * @param records
     */
    void executeAction(List<OrderSheetRecord> records);
}

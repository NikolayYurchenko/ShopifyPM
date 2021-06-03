package com.eliftech.shopify.service.contract;

import com.eliftech.shopify.model.GoogleClientInfo;
import com.eliftech.shopify.model.OrderSheetRecord;
import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;
import java.util.List;

public interface GoogleApp {

    /**
     * Authorize in google app
     * @param clientInfo
     * @return
     */
    Credential authorize(GoogleClientInfo clientInfo);

    /**
     * Execute action(e.g add data to google table)
     * @param records
     */
    void executeAction(List<OrderSheetRecord> records) throws IOException;
}

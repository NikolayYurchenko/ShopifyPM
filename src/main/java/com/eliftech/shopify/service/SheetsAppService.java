package com.eliftech.shopify.service;

import com.eliftech.shopify.config.SheetsStorageProperties;
import com.eliftech.shopify.data.entity.TableConfiguration;
import com.eliftech.shopify.data.service.TableConfigurationDataService;
import com.eliftech.shopify.model.GoogleClientInfo;
import com.eliftech.shopify.model.OrderSheetRecord;
import com.eliftech.shopify.service.contract.GoogleApp;
import com.eliftech.shopify.util.PartitionUtil;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SheetsAppService implements GoogleApp {

    private static final String APP_NAME = "ShopifyManager";

    @Value("${shopify.rest.orders.sheetsEntriesLimit}")
    private int limitOfSheetRecords;

    @Value("${shopify.rest.orders.requestTimeoutInMillis}")
    private long requestTimeoutInMillis;

    @Autowired
    private TableConfigurationDataService configurationDataService;

    @Autowired
    private AuthorizationCodeFlow authFlow;

    @Autowired
    private SheetsStorageProperties sheetsStorageProperties;

    @SneakyThrows
    @Override
    public Credential authorize(GoogleClientInfo clientInfo) {

        try {

            TokenResponse tokenResponse = new TokenResponse();

            tokenResponse.setAccessToken(clientInfo.getAccessToken());
            tokenResponse.setExpiresInSeconds(clientInfo.getExpiresInSeconds());
            tokenResponse.setTokenType("Bearer");
            tokenResponse.setRefreshToken("refreshToken");
            tokenResponse.setScope(SheetsScopes.SPREADSHEETS);

            return authFlow.createAndStoreCredential(tokenResponse, "user");

        } catch (Exception e) {

            log.error("Failed login to google , cause:[{}]", e.getMessage());

            throw new RuntimeException("Failed login to google");
        }
    }

    @Override
    @SneakyThrows
    public void executeAction(List<OrderSheetRecord> records) {

        Sheets sheets = this.getSheetManager();

        List<List<OrderSheetRecord>> partitionOfSheetRecords = PartitionUtil.ofSize(records, this.limitOfSheetRecords);

        for (List<OrderSheetRecord> sheetRecordsChunk : partitionOfSheetRecords) {

            sheetRecordsChunk.forEach(record -> {

                ValueRange dataBody = new ValueRange().setValues(record.prettifyForSheets());

                try {

                    log.info(dataBody.toString());

                    TableConfiguration configuration = configurationDataService.findByType(record.getFactoryType());

                    AppendValuesResponse appendBody = sheets.spreadsheets().values()
                            .append(configuration.getTableUid(), sheetsStorageProperties.getSheetName(), dataBody)
                            .setValueInputOption(sheetsStorageProperties.getInputOption())
                            .setInsertDataOption(sheetsStorageProperties.getInsertDataOption())
                            .setIncludeValuesInResponse(true)
                            .setPrettyPrint(true)
                            .setResponseValueRenderOption("FORMATTED_VALUE")
                            .execute();

                    log.info("Just added to google sheets:[{}]", appendBody);

                } catch (Exception e) {

                    log.info("Failed add data to table, cause:[{}]", e.getMessage());

                    throw new RuntimeException(e.getMessage());
                }
            });

            Thread.sleep(this.requestTimeoutInMillis);
        }
    }

    @SneakyThrows
    private Sheets getSheetManager() {

        Credential credential = authFlow.loadCredential("user");

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APP_NAME)
                .build();
    }
}

package com.eliftech.shopify.service;

import com.eliftech.shopify.config.SheetsStorageProperties;
import com.eliftech.shopify.model.OrderSheetRecord;
import com.eliftech.shopify.service.contract.GoogleApp;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
public class SheetsAppService implements GoogleApp {

    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String APP_NAME = "ShopifyPM";

    @Value("${shopify.tables.first}")
    private String firstTableUid;

    @Value("${shopify.tables.second}")
    private String secondTableUid;

    @Value("${shopify.tables.third}")
    private String thirdTableUid;

    @Value("${shopify.tables.fourth}")
    private String fourthTableUid;

    @Value("${shopify.tables.fifth}")
    private String fifthTableUid;

    @Autowired
    private SheetsStorageProperties sheetsStorageProperties;

    @SneakyThrows
    @Override
    public Credential authorize(String resourcePath) {

        InputStream resource = SheetsAppService.class.getResourceAsStream(resourcePath);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(resource));

        List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                 GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType(sheetsStorageProperties.getAccessType())
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    @Override
    @SneakyThrows
    public void executeAction(List<OrderSheetRecord> records) {

        Sheets sheets = this.getSheetManager();

        records.forEach(record -> {

            ValueRange dataBody = new ValueRange().setValues(record.prettifyForSheets());

            try {

                String tableUid = switch (record.getFactoryType()) {
                    case FIRST -> this.firstTableUid;
                    case SECOND -> this.secondTableUid;
                    case THIRD -> this.thirdTableUid;
                    case FOURTH -> this.fourthTableUid;
                    case FIFTH -> this.fifthTableUid;
                };

                AppendValuesResponse appendBody = sheets.spreadsheets().values()
                        .append(tableUid, sheetsStorageProperties.getSheetName(), dataBody)
                        .setValueInputOption(sheetsStorageProperties.getInputOption())
                        .setInsertDataOption(sheetsStorageProperties.getInsertDataOption())
                        .setIncludeValuesInResponse(true)
                        .execute();

                log.info("Just added to google sheets:[{}]", appendBody);

            } catch (Exception e) {

                log.info("Failed add data to table, cause:[{}]", e.getMessage());
            }
        });
    }

    @SneakyThrows
    private Sheets getSheetManager() {

        Credential credential = this.authorize("/credentials.json");

        return new Sheets.Builder( GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APP_NAME)
                .build();
    }
}

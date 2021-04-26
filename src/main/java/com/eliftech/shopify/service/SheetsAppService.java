package com.eliftech.shopify.service;

import com.eliftech.shopify.data.entity.GoogleCredentials;
import com.eliftech.shopify.data.entity.TableConfiguration;
import com.eliftech.shopify.data.service.GoogleCredentialsDataService;
import com.eliftech.shopify.data.service.TableConfigurationDataService;
import com.eliftech.shopify.model.GoogleClientInfo;
import com.eliftech.shopify.model.OrderSheetRecord;
import com.eliftech.shopify.rest.ShopifyRestRepository;
import com.eliftech.shopify.service.contract.GoogleApp;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
public class SheetsAppService implements GoogleApp {

    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String APP_NAME = "ShopifyManager";

    @Autowired
    private TableConfigurationDataService configurationDataService;

    @Autowired
    private GoogleCredentialsDataService credentialsDataService;

    @Autowired
    private ShopifyRestRepository shopifyRestRepository;

    @SneakyThrows
    @Override
    public Credential authorize(GoogleClientInfo clientInfo) {

        try {

            InputStream resource = SheetsAppService.class.getResourceAsStream("/credentials.json");

            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(resource));

            List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, scopes)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();

            TokenResponse tokenResponse = new TokenResponse();

            tokenResponse.setAccessToken(clientInfo.getAccessToken());
            tokenResponse.setExpiresInSeconds(clientInfo.getExpiresInSeconds());
            tokenResponse.setTokenType("Bearer");
            tokenResponse.setScope(SheetsScopes.SPREADSHEETS);

            return flow.createAndStoreCredential(tokenResponse, null);

        } catch (Exception e) {

            log.error("Failed login to google , cause:[{}]", e.getMessage());

            throw new RuntimeException("Failed login to google");
        }
    }

    @Override
    @SneakyThrows
    public void executeAction(List<OrderSheetRecord> records) {

//        Sheets sheets = this.getSheetManager();

        records.forEach(record -> {

            ValueRange dataBody = new ValueRange().setValues(record.prettifyForSheets());

            try {

                log.info(dataBody.toString());

                TableConfiguration configuration = configurationDataService.findByType(record.getFactoryType());

                GoogleCredentials credentials = credentialsDataService.findFirst();

                AppendValuesResponse appendBody = shopifyRestRepository.appendValuesToSheet(dataBody.getValues(), configuration, credentials.getApiKey());

                log.info("Just added to google sheets:[{}]", appendBody);

            } catch (Exception e) {

                log.info("Failed add data to table, cause:[{}]", e.getMessage());
            }
        });
    }

    @SneakyThrows
    private Sheets getSheetManager() {

//        GoogleCredentials credential = this.authorize();

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), null)
                .setApplicationName(APP_NAME)
                .setGoogleClientRequestInitializer(new SheetsRequestInitializer("ya29.a0AfH6SMD7qm0T3_UReXo1HSEc1IEj0DWLXTthhSevhSk6wCe0lAuVRirQcbaq8oZz2LtIYBX33VPymSy7INjx2T4uAZijLHjX85TTPP3gIrvUcHsX2IbhUaB2YxLR4ykUebF6wADZExrNR2TqEF23ar6ttHy8"))
                .setSheetsRequestInitializer(new SheetsRequestInitializer("ya29.a0AfH6SMD7qm0T3_UReXo1HSEc1IEj0DWLXTthhSevhSk6wCe0lAuVRirQcbaq8oZz2LtIYBX33VPymSy7INjx2T4uAZijLHjX85TTPP3gIrvUcHsX2IbhUaB2YxLR4ykUebF6wADZExrNR2TqEF23ar6ttHy8"))
                .build();
    }

}

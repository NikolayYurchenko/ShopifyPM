package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.GoogleCredentials;
import com.eliftech.shopify.data.repository.GoogleCredentialsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GoogleCredentialsDataService {

    private final GoogleCredentialsRepository credentialsRepository;

    /**
     * Create google credentials
     * @param email
     * @param apiKey
     */
    public void create(String email, String apiKey) {

        log.debug("Creating google credentials for service account with email:[{}]", email);

        GoogleCredentials credentials = GoogleCredentials.builder()
                .email(email)
                .apiKey(apiKey)
                .build();

        credentialsRepository.save(credentials);
    }

    /**
     * Find all google credentials
     * @return
     */
    public List<GoogleCredentials> findAll() {

        log.debug("Searching all google credentials");

        List<GoogleCredentials> credentials = credentialsRepository.findAll();

        log.debug("...found:[{}]", credentials.size());

        return credentials;
    }

    /**
     * Find first google credentials
     * @return
     */
    public GoogleCredentials findFirst() {

        log.debug("Searching for first google credentials");

        GoogleCredentials credentials = credentialsRepository.findTop1ByOrderByCreatedAt()
                .orElseThrow(() -> new EntityNotFoundException("Not found any google credentials"));

        log.debug("...found:[{}]", credentials);

        return credentials;
    }
}

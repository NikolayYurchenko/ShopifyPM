package com.eliftech.shopify.rest;

import com.eliftech.shopify.rest.exception.RestRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
public abstract class BaseRestRepository {

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * Sync request
     *
     * @param method
     * @param path
     * @param model
     * @param aClass
     * @param headers
     * @throws RestRequestException
     * @return
     */
    protected ResponseEntity<?> executeSync(HttpMethod method, String path, Object model, Class aClass, HttpHeaders headers) throws RestRequestException {

        StopWatch sw = new StopWatch();
        sw.start();

        log.info("Send [{}] request to [path:{}]", method, path);

        ResponseEntity<?> result;

        if (headers.isEmpty()) {
            headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
        }

        try {

            result = restTemplate.exchange(path, method, new HttpEntity<>(model, headers), aClass);

        } catch (Exception e) {

            log.error("Error when send request to [{}], cause - [{}]", path, e.getMessage());

            sw.stop();

            throw new RestRequestException("General rest request error, cause:["+ e.getMessage() +"]");
        }

        sw.stop();
        log.info("Timed [{}] ms [{}]", sw.getLastTaskTimeMillis(), path);

        return result;
    }
    protected  ResponseEntity<?> executeSync(HttpMethod method, URI path, Object model, Class aClass, HttpHeaders headers) throws RestRequestException {

        StopWatch sw = new StopWatch();
        sw.start();

        log.info("Send [{}] request to [path:{}]", method, path.toString());

        ResponseEntity<?> result;

        if (headers.isEmpty()) {
            headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
        }

        try {
            result = restTemplate.exchange(path, method, new HttpEntity<>(model, headers), aClass);

        } catch (Exception e) {

            log.error("Error when send request to [{}], cause - [{}]", path.toString(), e.getMessage());

            sw.stop();

            throw new RestRequestException("General rest request error, cause:["+ e.getMessage() +"]");
        }

        sw.stop();
        log.info("Timed [{}] ms [{}]", sw.getLastTaskTimeMillis(), path.toString());

        return result;
    }
}

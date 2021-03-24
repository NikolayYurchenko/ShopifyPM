package com.eliftech.shopify.rest;

import com.eliftech.shopify.rest.exception.RestRequestException;
import com.eliftech.shopify.rest.model.ProductListResponse;
import com.eliftech.shopify.rest.model.ProductRestResponse;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
public class ShopifyRestRepository extends BaseRestRepository {

    @Value("${shopify.rest.basePath}")
    private String host;

    private final String AUTH_HEADER = "X-Shopify-Access-Token";

    private final String PRODUCTS_POSTFIX = "products.json";
    private final String ORDERS_POSTFIX = "orders.json";

    @SneakyThrows
    @SuppressWarnings("all")
    public List<ProductRestResponse> getActualProducts(String storeName, String pasword, @Nullable  String sinceId) {

        try {

            log.info("Try to get products from store:[{}]", storeName);

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTH_HEADER, pasword);

            URI fullPath = new URIBuilder()
                    .setScheme("https")
                    .setHost(storeName + "." + host + "/")
                    .setPath(PRODUCTS_POSTFIX)
                    .build();

            ResponseEntity<ProductListResponse> response = super.executeSync(HttpMethod.GET, fullPath.toString(), null,  ProductListResponse.class, headers);

            log.info("...receive response:[{}]", response.getBody().getProducts().size());

            return response.getBody().getProducts();

        } catch (Exception e) {

            log.error("Something when wrong when try get producsts from store:[{}], cause:[{}]", storeName, e.getMessage());

            throw new RestRequestException(e.getMessage());
        }
    }
}

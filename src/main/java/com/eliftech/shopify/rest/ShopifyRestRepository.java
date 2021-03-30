package com.eliftech.shopify.rest;

import com.eliftech.shopify.rest.exception.RestRequestException;
import com.eliftech.shopify.rest.model.*;
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

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShopifyRestRepository extends BaseRestRepository {

    @Value("${shopify.rest.basePath}")
    private String host;

    private final String AUTH_HEADER = "X-Shopify-Access-Token";
    private final String PRODUCT_LIMIT = "70";
    private final String NEXT_BATCH_HEADER = "link";

    private final String PRODUCTS_POSTFIX = "products.json";
    private final String ORDERS_POSTFIX = "orders.json";
    private final String INVENTORY_POSTFIX = "inventory_items.json";

    private final String API_EXTENSION = ".json";

    private final Map<String, List<ProductRestResponse>> allProducts = new HashMap<>();

    @SneakyThrows
    @SuppressWarnings("all")
    public List<ProductRestResponse> getActualProducts(String storeName, String password) {

        try {

            log.info("Try to get products from store:[{}]", storeName);

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTH_HEADER, password);

            URI fullPath = new URIBuilder()
                    .setScheme("https")
                    .setHost(storeName + "." + host + "/")
                    .setParameter("limit", PRODUCT_LIMIT)
                    .setPath(PRODUCTS_POSTFIX)
                    .build();

            ResponseEntity<ProductListResponse> response = super.executeSync(HttpMethod.GET, fullPath.toString(), null,  ProductListResponse.class, headers);

            log.info("...receive response:[{}]", response.getBody().getProducts().size());

            this.allProducts.put(fullPath.toString(), response.getBody().getProducts());

            Optional<String> nextLinkForBatch = Optional.ofNullable(response.getHeaders().getFirst(NEXT_BATCH_HEADER));

            this.getAnotherBatchIfNeed(nextLinkForBatch.orElse(null), password);

            return allProducts.values().stream().flatMap(List::stream).collect(Collectors.toList());

        } catch (Exception e) {

            log.error("Something when wrong when try get producsts from store:[{}], cause:[{}]", storeName, e.getMessage());

            throw new RestRequestException(e.getMessage());
        }
    }

    @SneakyThrows
    @SuppressWarnings("all")
    public ProductRestResponse getProductById(String storeName, String productId, String password) {

        try {

            log.info("Try to get product with id:[{}] from store:[{}]", productId, storeName);

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTH_HEADER, password);

            URI fullPath = new URIBuilder()
                    .setScheme("https")
                    .setHost(storeName + "." + host + "/")
                    .setPath(PRODUCTS_POSTFIX.replace(API_EXTENSION, "") + "/" + productId + API_EXTENSION)
                    .build();

            ResponseEntity<ProductResponseWrapper> response = super.executeSync(HttpMethod.GET, fullPath.toString(), null,  ProductResponseWrapper.class, headers);

            log.info("...receive response:[{}]", response.getBody());

            return response.getBody().getProduct();

        } catch (Exception e) {

            log.error("Something when wrong when try get product with id:[{}] from store:[{}], cause:[{}]", productId, storeName, e.getMessage());

            throw new RestRequestException(e.getMessage());
        }
    }

    @SneakyThrows
    @SuppressWarnings("all")
    private void getAnotherBatchIfNeed(String linkForBatch, String password) {

        if (linkForBatch != null) {

            log.info("Getting another batch, link:[{}]", linkForBatch);

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTH_HEADER, password);

            try {

                linkForBatch = ProductRestResponse.formatNextBatchLink(linkForBatch);

                ResponseEntity<ProductListResponse> response = super.executeSync(HttpMethod.GET, linkForBatch, null,  ProductListResponse.class, headers);

                log.info("...receive response:[{}]", response.getBody().getProducts().size());

                Optional<String> linkHeader = Optional.ofNullable(response.getHeaders().getFirst(NEXT_BATCH_HEADER));

                String nextLinkForBatch = ProductRestResponse.parseNextBatchLinkAndFormat(linkHeader.orElse(null));

                this.allProducts.put(linkForBatch, response.getBody().getProducts());

                this.getAnotherBatchIfNeed(nextLinkForBatch, password);

            } catch (Exception e) {

                log.error("Something when wrong when try get producsts:[{}], cause:[{}]", e.getMessage());

                throw new RestRequestException(e.getMessage());
            }
        }
    }

    @SneakyThrows
    @SuppressWarnings("all")
    protected InventoryListResponse getInventories(String storeName, List<String> productIds, String password) {

        try {

            log.info("Getting inventory by products:{}", productIds);

            URI fullPath = new URIBuilder()
                    .setScheme("https")
                    .setHost(storeName + "." + host + "/")
                    .setPath(INVENTORY_POSTFIX)
                    .setParameter("ids", productIds.toString())
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTH_HEADER, password);

            ResponseEntity<InventoryListResponse> response = super.executeSync(HttpMethod.GET, fullPath.toString(), null,  InventoryListResponse.class, headers);

            log.info("...receive response:[{}]", response.getBody().getInventoryItems().size());

            return response.getBody();

        } catch (Exception e) {

            log.error("Something when wrong when try get inventories:[{}], cause:[{}]", e.getMessage());

            throw new RestRequestException(e.getMessage());
        }
    }
}

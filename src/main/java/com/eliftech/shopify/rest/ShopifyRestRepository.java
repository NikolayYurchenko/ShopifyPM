package com.eliftech.shopify.rest;

import com.eliftech.shopify.rest.exception.RestRequestException;
import com.eliftech.shopify.rest.model.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final String PRODUCT_LIMIT = "150";
    private final String ORDER_LIMIT = "250";
    private final String NEXT_BATCH_HEADER = "link";

    private final String PRODUCTS_POSTFIX = "products.json";
    private final String ORDERS_POSTFIX = "orders.json";

    private final String API_EXTENSION = ".json";
    private final String START_DATE = "2000-01-01T13:14";

    private  Map<String, List<ProductRestResponse>> allProducts = new HashMap<>();
    private  Map<String, List<OrderRestResponse>> allOrders = new HashMap<>();

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

            this.getAnotherBatchIfNeed(nextLinkForBatch.orElse(null), ProductListResponse.class, TargetType.PRODUCTS, password);

            List<ProductRestResponse> products = allProducts.values().stream().flatMap(List::stream).collect(Collectors.toList());

            allProducts = new HashMap<>();

            return products;

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
    private synchronized void getAnotherBatchIfNeed(String linkForBatch, Class classToCast, TargetType type, String password) {

        if (linkForBatch != null) {

            log.info("Getting another batch, link:[{}]", linkForBatch);

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTH_HEADER, password);

            try {

                linkForBatch = ProductRestResponse.formatNextBatchLink(linkForBatch);

                ResponseEntity<?> response = super.executeSync(HttpMethod.GET, linkForBatch, null,  classToCast, headers);

                boolean batchForProducts = type.equals(TargetType.PRODUCTS);

                response = batchForProducts
                        ? ResponseEntity.of(Optional.of((ProductListResponse) response.getBody()))
                        : ResponseEntity.of(Optional.of((OrderListResponse) response.getBody()));

                log.info("...receive response:[{}]", response.getBody());

                Optional<String> linkHeader = Optional.ofNullable(response.getHeaders().getFirst(NEXT_BATCH_HEADER));

                String nextLinkForBatch = ProductRestResponse.parseNextBatchLinkAndFormat(linkHeader.orElse(null));

                if (batchForProducts) {

                    this.allProducts.put(linkForBatch, ((ProductListResponse) response.getBody()).getProducts());

                } else {

                    this.allOrders.put(linkForBatch, ((OrderListResponse) response.getBody()).getOrders());
                }

                this.getAnotherBatchIfNeed(nextLinkForBatch, classToCast, type, password);

            } catch (Exception e) {

                log.error("Something when wrong when try get producsts:[{}], cause:[{}]", e.getMessage());

                throw new RestRequestException(e.getMessage());
            }
        }
    }

    @SneakyThrows
    @SuppressWarnings("all")
    public ProductRestResponse updateProduct(String storeName, String productId, UpdateProductRequest request, String password) {

        try {

            log.info("Updating product by id:[{}]", productId);

            URI fullPath = new URIBuilder()
                    .setScheme("https")
                    .setHost(storeName + "." + host + "/")
                    .setPath(PRODUCTS_POSTFIX.replace(API_EXTENSION, "") + "/" + productId + API_EXTENSION)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTH_HEADER, password);

            ResponseEntity<ProductResponseWrapper> response = super.executeSync(HttpMethod.PUT, fullPath.toString(),
                    new UpdateProductWrapper(request),  ProductResponseWrapper.class, headers);

            log.info("...receive response:[{}]", response.getBody().getProduct());

            return response.getBody().getProduct();

        } catch (Exception e) {

            log.error("Something when wrong when try update product:[{}], cause:[{}]", e.getMessage());

            throw new RestRequestException(e.getMessage());
        }
    }

    @SneakyThrows
    @SuppressWarnings("all")
    public List<OrderRestResponse> getOrders(String storeName, String dateMin, String password) {

        try {

            log.info("Sync orders after date:[{}]", dateMin);

            URI fullPath = new URIBuilder()
                    .setScheme("https")
                    .setHost(storeName + "." + host + "/")
                    .setPath(ORDERS_POSTFIX)
                    .setParameter("created_at_min", dateMin != null ? dateMin : START_DATE)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTH_HEADER, password);

            ResponseEntity<OrderListResponse> response = super.executeSync(HttpMethod.GET, fullPath.toString(), null,  OrderListResponse.class, headers);

            log.info("...receive response:[{}]", response.getBody().getOrders().size());

            this.allOrders.put(fullPath.toString(), response.getBody().getOrders());

            Optional<String> nextLinkForBatch = Optional.ofNullable(response.getHeaders().getFirst(NEXT_BATCH_HEADER));

            this.getAnotherBatchIfNeed(nextLinkForBatch.orElse(null), OrderListResponse.class, TargetType.ORDERS, password);

            List<OrderRestResponse> orders = allOrders.values().stream().flatMap(List::stream).collect(Collectors.toList());

            allOrders = new HashMap<>();

            return orders;

        } catch (Exception e) {

            log.error("Something when wrong when try get orders:[{}], cause:[{}]", e.getMessage());

            throw new RestRequestException(e.getMessage());
        }
    }
}

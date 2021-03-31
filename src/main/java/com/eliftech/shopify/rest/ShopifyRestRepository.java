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
import java.util.*;
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

    private final String API_EXTENSION = ".json";

    private final Map<String, List<ProductRestResponse>> allProducts = new HashMap<>();

//    @PostConstruct
//    public void init() {
//
//        UpdateProductRequest request = new UpdateProductRequest();
//        request.setBodyHtml("<p> My description</p>");
//        request.setHandle("my handle");
//        request.setProductType("my product type");
//        request.setStatus(ProductStatus.ACTIVE.getStatusName());
//        request.setTags("my tags");
//        request.setVariants(Collections.emptyList());
//        request.setVendor("my vendor");
//        request.setTitle("My product");
//        request.setVariants(List.of(new VariantUpdateRequest("37038798536863", "50", "Updating the Product SKU")));
//
//        this.updateProduct("test-eliftech-store", "5893179408543", new UpdateProductWrapper(request),
//                "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");
//    }

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

            log.error("Something when wrong when try get inventories:[{}], cause:[{}]", e.getMessage());

            throw new RestRequestException(e.getMessage());
        }
    }
}

package com.eliftech.shopify.amqp.publisher;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSyncRequest implements Serializable {

    private String storeName;

    private String productUid;

    public static ProductSyncRequest instance(String storeName, @Nullable String productUid) {

        return new ProductSyncRequest(storeName, productUid == null? "" : productUid);
    }
}

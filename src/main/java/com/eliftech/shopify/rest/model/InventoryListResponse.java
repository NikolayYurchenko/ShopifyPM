package com.eliftech.shopify.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryListResponse {

    private List<InventoryItemResponse> inventoryItems;
}

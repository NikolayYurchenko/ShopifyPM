package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.Order;
import com.eliftech.shopify.data.repository.OrderRepository;
import com.eliftech.shopify.rest.model.OrderRestResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderDataService {

    private final OrderRepository orderRepository;

    /**
     * Create orders
     * @param orderForms
     * @param storeUid
     */
    @Transactional
    public void create(List<OrderRestResponse> orderForms, String storeUid) {

        log.info("Creating:[{}] orders", orderForms.size());

        List<Order> orders = orderForms.stream().map(order ->
                Order.builder()
                .storeUid(storeUid)
                .closedAt(order.getClosedAt())
                .confirmed(order.isConfirmed())
                .createdAt(order.getCreatedAt())
                .currency(order.getCurrency())
                .email(order.getEmail())
                .externalId(order.getId())
                .financialStatus(order.getFinancialStatus())
                .name(order.getName())
                .note(order.getNote())
                .number(order.getNumber())
                .phone(order.getPhone())
                .reference(order.getReference())
                .subtotalPrice(order.getSubtotalPrice())
                .taxesIncluded(order.isTaxesIncluded())
                .totalDiscounts(order.getTotalDiscounts())
                .totalLineItemsPrice(order.getTotalLineItemsPrice())
                .totalPrice(order.getTotalPrice())
                .totalTax(order.getTotalTax())
                .totalWeight(order.getTotalWeight())
                .updatedAt(order.getUpdatedAt())
                .userId(order.getUserId())
                .build()).collect(Collectors.toList());

        orderRepository.saveAll(orders);
    }


    /**
     * Find all orders by store uuid
     * @param storeUid
     * @param page
     * @param limit
     * @return
     */
    public List<Order> findAllByStoreUid(String storeUid, int page, int limit) {

        log.info("Searching orders by store uuid:[{}]", storeUid);

        Page<Order> orders = orderRepository.findAllByStoreUid(storeUid, PageRequest.of(page, limit));

        log.info("...found:[{}]", orders.getContent().size());

        return orders.getContent();
    }

    /**
     * Get last order created at date
     * @return
     */
    public Optional<String> getCreatedAtFromLastOrder() {

        log.info("Getting created at from last order");

        Optional<Order> order = orderRepository.findTop1ByOrderByCreatedAtDesc();

        return order.map(Order::getCreatedAt);
    }
}

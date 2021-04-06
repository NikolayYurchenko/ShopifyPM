package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.Order;
import com.eliftech.shopify.data.repository.OrderRepository;
import com.eliftech.shopify.rest.model.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
     */
    @Transactional
    public void create(List<OrderResponse> orderForms) {

        log.info("Creating:[{}] orders", orderForms.size());

        List<Order> orders = orderForms.stream().map(order ->
                Order.builder()
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
     * Get last order created at date
     * @return
     */
    public Optional<String> getCreatedAtFromLastOrder() {

        log.info("Getting created at from last order");

        Optional<Order> order = orderRepository.findTop1ByOrderByCreatedAtDesc();

        return order.map(Order::getCreatedAt);
    }
}

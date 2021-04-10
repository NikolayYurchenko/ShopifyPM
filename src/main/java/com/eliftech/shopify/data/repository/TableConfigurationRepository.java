package com.eliftech.shopify.data.repository;

import com.eliftech.shopify.data.entity.TableConfiguration;
import com.eliftech.shopify.model.FactoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableConfigurationRepository extends JpaRepository<TableConfiguration, Long> {

    Optional<TableConfiguration> findByType(FactoryType type);
}

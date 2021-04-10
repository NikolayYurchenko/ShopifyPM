package com.eliftech.shopify.data.service;


import com.eliftech.shopify.data.entity.TableConfiguration;
import com.eliftech.shopify.data.repository.TableConfigurationRepository;
import com.eliftech.shopify.model.FactoryType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TableConfigurationDataService {

    private final TableConfigurationRepository configurationRepository;

    /**
     * Create table configuration
     * @param tableUid
     * @param type
     */
    public void create(String tableUid, FactoryType type) {

        log.info("Creating table configuration for factory type:[{}], table uuid:[{}]", type, tableUid);

        TableConfiguration configuration = TableConfiguration.builder()
                .tableUid(tableUid)
                .type(type)
                .build();

        configurationRepository.save(configuration);
    }

    /**
     * Find all table configurations
     * @return
     */
    public List<TableConfiguration> findAll() {

        log.info("Searching all table configurations");

        return configurationRepository.findAll();
    }

    /**
     * Find by type
     * @param type
     * @return
     */
    public TableConfiguration findByType(FactoryType type) {

        log.info("Searching table configuration by type:[{}]", type);

        TableConfiguration configuration = configurationRepository.findByType(type)
                .orElseThrow(() ->  new EntityNotFoundException("Not found configuration by type:["+ type +"]"));

        log.info("...found:[{}]", configuration);

        return configuration;
    }
}

package com.hyatt.notificationservice.service.impl;

import com.hyatt.notificationservice.exception.ResourceNotFoundException;
import com.hyatt.notificationservice.model.Configuration;
import com.hyatt.notificationservice.repository.ConfigurationRepository;
import com.hyatt.notificationservice.service.ConfigurationService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final String RESOURCE_NAME = "configuration";
    private static final String FIELD_NAME_DEPARTMENT_ID = "name";

    private final ConfigurationRepository configurationRepository;

    @Override
    public List<Configuration> findAll() {
        log.info("Searching all configuration!");
        return Lists.newArrayList(configurationRepository.findAll());
    }

    @Override
    public Configuration findByName(String name) {
        log.info("Searching configuration: {}", name);
        return configurationRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, FIELD_NAME_DEPARTMENT_ID, name));
    }

    @Override
    public void save(Configuration configuration) {
        log.info("Saving configuration: {}", configuration);
        if (isConfigurationValid(configuration)) {
            configurationRepository.save(configuration);
        } else {
            log.warn("Configuration is invalid!");
        }
    }

    @Override
    public void update(Configuration configuration) {
        log.info("Updating configuration: {}", configuration);
        if (isConfigurationValid(configuration)) {
            configurationRepository.findById(configuration.getName()).ifPresent(confDb -> {
                confDb.setValue(configuration.getValue());
                configurationRepository.save(confDb);
            });
        } else {
            log.warn("Configuration is invalid!");
        }
    }

    @Override
    public void delete(Configuration configuration) {
        log.info("Deleting configuration: {}", configuration);
        configurationRepository.delete(configuration);
    }

    @Override
    public void deleteByName(String name) {
        log.info("Deleting configuration: {}", name);
        configurationRepository.deleteById(name);
    }

    private boolean isConfigurationValid(Configuration configuration) {
        return configuration != null &&
                StringUtils.isNotBlank(configuration.getName()) &&
                StringUtils.isNotBlank(configuration.getValue());
    }
}

package com.hyatt.notificationservice.service;


import com.hyatt.notificationservice.model.Configuration;

import java.util.List;

/**
 * Configuration Service contains CRUD operations
 */
public interface ConfigurationService {

    List<Configuration> findAll();

    Configuration findByName(String name);

    void save(Configuration configuration);

    void update(Configuration configuration);

    void delete(Configuration configuration);

    void deleteByName(String name);
}

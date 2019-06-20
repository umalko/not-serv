package com.example.notificationservice.service;


import com.example.notificationservice.model.Configuration;

import java.util.List;

public interface ConfigurationService {

    List<Configuration> findAll();

    Configuration findByName(String name);

    void save(Configuration configuration);

    void update(Configuration configuration);

    void delete(Configuration configuration);

    void deleteByName(String name);
}

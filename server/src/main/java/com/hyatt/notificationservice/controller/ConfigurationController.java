package com.hyatt.notificationservice.controller;

import com.hyatt.notificationservice.model.Configuration;
import com.hyatt.notificationservice.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Configuration Controller contains CRUD operations. Basic name - value object.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/configurations")
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

    /**
     * Find all configurations.
     *
     * @return list of configurations. (name - value)
     */
    @GetMapping
    public List<Configuration> findAll() {
        return configurationService.findAll();
    }

    /**
     * Find configuration by name.
     *
     * @param name configuration name
     * @return configuration object. (name - value)
     */
    @GetMapping("/name/{name}")
    public Configuration getByName(@PathVariable("name") String name) {
        return configurationService.findByName(name);
    }

    /**
     * Creates configuration object. (name - value)
     *
     * @param configuration configuration object. (name - value)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Configuration configuration) {
        configurationService.save(configuration);
    }

    /**
     * Updates configuration value.
     *
     * @param configuration configuration object. (name - value)
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Configuration configuration) {
        configurationService.update(configuration);
    }
}

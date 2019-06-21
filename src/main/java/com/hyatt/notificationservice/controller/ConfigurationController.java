package com.hyatt.notificationservice.controller;

import com.hyatt.notificationservice.model.Configuration;
import com.hyatt.notificationservice.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/configurations")
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping
    public List<Configuration> findAll() {
        return configurationService.findAll();
    }

    @GetMapping("/name/{name}")
    public Configuration getByName(@PathVariable("name") String name) {
        return configurationService.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Configuration configuration) {
        configurationService.save(configuration);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Configuration configuration) {
        configurationService.update(configuration);
    }
}

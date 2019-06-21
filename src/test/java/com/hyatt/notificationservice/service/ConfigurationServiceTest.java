package com.hyatt.notificationservice.service;

import com.hyatt.notificationservice.exception.ResourceNotFoundException;
import com.hyatt.notificationservice.model.Configuration;
import com.hyatt.notificationservice.repository.ConfigurationRepository;
import com.hyatt.notificationservice.service.impl.ConfigurationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.hyatt.notificationservice.util.ConfigurationFiller.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class ConfigurationServiceTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;

    @Test
    public void findAll() {
        Mockito.when(configurationRepository.findAll()).thenReturn(CONFIGURATIONS);

        List<Configuration> all = configurationService.findAll();

        assertThat(all).hasSize(CONFIGURATIONS.size());
    }

    @Test
    public void findByName() {
        Mockito.when(configurationRepository.findById(CONFIGURATION_NAME_1)).thenReturn(Optional.of(CONFIGURATION_1));

        Configuration configuration = configurationService.findByName(CONFIGURATION_NAME_1);

        assertThat(configuration).isEqualTo(CONFIGURATION_1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByName_throwNotFound() {
        Mockito.when(configurationRepository.findById(CONFIGURATION_NAME_1)).thenReturn(Optional.empty());
        configurationService.findByName(CONFIGURATION_NAME_1);
    }

    @Test
    public void save_correctInputVariables() {
        configurationService.save(CONFIGURATION_1);
        int expectedNumberOfTheMethodInvocations = 1;

        then(configurationRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(CONFIGURATION_1);
    }

    @Test
    public void save_wrongInputVariables() {
        configurationService.save(CONFIGURATION_WRONG_NAME);
        configurationService.save(CONFIGURATION_WRONG_VALUE);
        int expectedNumberOfTheMethodInvocations = 0;

        then(configurationRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(CONFIGURATION_1);
    }

    @Test
    public void update() {
        when(configurationRepository.findById(CONFIGURATION_NAME_1)).thenReturn(Optional.of(CONFIGURATION_1));
        configurationService.update(CONFIGURATION_1);

        int expectedNumberOfTheMethodInvocations = 1;

        then(configurationRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(CONFIGURATION_1);
    }

    @Test
    public void update_withNotExistConfiguration() {
        when(configurationRepository.findById(CONFIGURATION_NAME_1)).thenReturn(Optional.empty());
        configurationService.update(CONFIGURATION_1);
        int expectedNumberOfTheMethodInvocations = 0;

        then(configurationRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(CONFIGURATION_1);
    }

    @Test
    public void update_wrongInputVariables() {
        configurationService.update(CONFIGURATION_WRONG_NAME);
        configurationService.update(CONFIGURATION_WRONG_VALUE);
        int expectedNumberOfTheMethodInvocations = 0;

        then(configurationRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(CONFIGURATION_1);
    }

    @Test
    public void delete() {
        configurationService.delete(CONFIGURATION_1);
        int expectedNumberOfTheMethodInvocations = 1;

        then(configurationRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .delete(CONFIGURATION_1);
    }

    @Test
    public void deleteByName() {
        configurationService.deleteByName(CONFIGURATION_NAME_1);
        int expectedNumberOfTheMethodInvocations = 1;

        then(configurationRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .deleteById(CONFIGURATION_NAME_1);
    }
}
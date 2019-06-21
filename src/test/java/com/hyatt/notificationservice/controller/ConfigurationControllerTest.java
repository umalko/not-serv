package com.hyatt.notificationservice.controller;

import com.hyatt.notificationservice.model.Configuration;
import com.hyatt.notificationservice.service.ConfigurationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.hyatt.notificationservice.util.ConfigurationFiller.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.then;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigurationController.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ConfigurationControllerTest {

    private static final String BASE_API = "/api/v1/configurations";
    private static final String GET_BY_NAME_API = "/api/v1/configurations/name/" + CONFIGURATION_NAME_1;

    private static final String NAME = "Omaha3sweww";
    private static final String VALUE = "Omeha3";

    private static final String BODY = "{\n" +
            "\t\"name\": \"" + NAME + "\",\n" +
            "\t\"value\": \"" + VALUE + "\"\n" +
            "}";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigurationService configurationService;

    @Test
    public void findAll() throws Exception {
        Mockito.when(configurationService.findAll()).thenReturn(CONFIGURATIONS);
        this.mockMvc.perform(
                get(BASE_API))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(new ObjectMapper().writeValueAsString(CONFIGURATIONS))));
    }

    @Test
    public void getByName() throws Exception {
        Mockito.when(configurationService.findByName(CONFIGURATION_NAME_1)).thenReturn(CONFIGURATION_1);
        this.mockMvc.perform(
                get(GET_BY_NAME_API))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(new ObjectMapper().writeValueAsString(CONFIGURATION_1))));
    }

    @Test
    public void create() throws Exception {
        this.mockMvc.perform(
                post(BASE_API)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(BODY))
                .andDo(print())
                .andExpect(status().isCreated());

        int expectedNumberOfTheMethodInvocations = 1;

        then(configurationService)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(new Configuration(NAME, VALUE));
    }

    @Test
    public void update() throws Exception {
        this.mockMvc.perform(
                put(BASE_API)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(BODY))
                .andDo(print())
                .andExpect(status().isOk());

        int expectedNumberOfTheMethodInvocations = 1;

        then(configurationService)
                .should(times(expectedNumberOfTheMethodInvocations))
                .update(new Configuration(NAME, VALUE));
    }
}
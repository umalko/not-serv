package com.hyatt.notificationservice.controller;

import com.hyatt.notificationservice.dto.DepartmentDto;
import com.hyatt.notificationservice.service.DepartmentService;
import com.hyatt.notificationservice.util.DepartmentFiller;
import com.hyatt.notificationservice.util.TransformDepartmentUtil;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.hyatt.notificationservice.util.DepartmentFiller.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.then;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class DepartmentControllerTest {

    private static final String BASE_API = "/api/v1/departments";
    private static final String GET_BY_ID_API = "/api/v1/departments//id/" + DepartmentFiller.DEPARTMENT_ID_1;

    private static final String ID = "Omeha3";
    private static final String NAME = "Omaha3sweww";

    private static final String BODY = "{\n" +
            "\t\"name\": \"" + NAME + "\",\n" +
            "\t\"departmentId\": \"" + ID + "\"\n" +
            "}";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private TransformDepartmentUtil transformDepartmentUtil = new TransformDepartmentUtil();

    @Test
    public void listDepartments() throws Exception {
        List<DepartmentDto> departmentDtos = transformDepartmentUtil.transformModelsToDtos(DEPARTMENTS);
        Mockito.when(departmentService.findAll()).thenReturn(departmentDtos);
        this.mockMvc.perform(
                get(BASE_API))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(new ObjectMapper().writeValueAsString(departmentDtos))));
    }

    @Test
    public void findByDepartmentId() throws Exception {
        DepartmentDto departmentDto1 = transformDepartmentUtil.transformModelToDto(DEPARTMENT_1);
        Mockito.when(departmentService.findByDepartmentId(DEPARTMENT_ID_1)).thenReturn(departmentDto1);
        this.mockMvc.perform(
                get(GET_BY_ID_API))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(new ObjectMapper().writeValueAsString(departmentDto1))));
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

        then(departmentService)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(new DepartmentDto(ID, NAME));
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

        then(departmentService)
                .should(times(expectedNumberOfTheMethodInvocations))
                .update(new DepartmentDto(ID, NAME));
    }

    @Test
    public void delete() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(GET_BY_ID_API))
                .andDo(print())
                .andExpect(status().isOk());

        int expectedNumberOfTheMethodInvocations = 1;

        then(departmentService)
                .should(times(expectedNumberOfTheMethodInvocations))
                .deleteByDepartmentId(DEPARTMENT_ID_1);
    }
}
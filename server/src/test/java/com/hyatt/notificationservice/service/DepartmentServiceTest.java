package com.hyatt.notificationservice.service;

import com.hyatt.notificationservice.dto.DepartmentDto;
import com.hyatt.notificationservice.exception.ResourceNotFoundException;
import com.hyatt.notificationservice.repository.DepartmentRepository;
import com.hyatt.notificationservice.service.impl.DepartmentServiceImpl;
import com.hyatt.notificationservice.util.TransformDepartmentUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.hyatt.notificationservice.util.DepartmentFiller.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

    private static final String DEPARTMENT_ID_PROPERTY = "departmentId";

    @Mock
    private DepartmentRepository departmentRepository;

    @Spy
    private TransformDepartmentUtil transformDepartmentUtil;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    public void findAll() {
        Mockito.when(departmentRepository.findAll()).thenReturn(DEPARTMENTS);

        List<DepartmentDto> all = departmentService.findAll();

        assertThat(all).hasSize(DEPARTMENTS.size());
    }

    @Test
    public void findByDepartmentId() {
        Mockito.when(departmentRepository.findByDepartmentId(DEPARTMENT_ID_1)).thenReturn(Optional.of(DEPARTMENT_1));

        DepartmentDto departmentDto = departmentService.findByDepartmentId(DEPARTMENT_ID_1);

        assertThat(departmentDto).isNotNull().hasFieldOrPropertyWithValue(DEPARTMENT_ID_PROPERTY, DEPARTMENT_ID_1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByName_throwNotFound() {
        Mockito.when(departmentRepository.findByDepartmentId(DEPARTMENT_ID_1)).thenReturn(Optional.empty());
        departmentService.findByDepartmentId(DEPARTMENT_ID_1);
    }

    @Test
    public void save() {
        departmentService.save(transformDepartmentUtil.transformModelToDto(DEPARTMENT_1));
        int expectedNumberOfTheMethodInvocations = 1;

        then(departmentRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(DEPARTMENT_1);
    }

    @Test
    public void update() {
        when(departmentRepository.findByDepartmentId(DEPARTMENT_ID_1)).thenReturn(Optional.of(DEPARTMENT_1));
        departmentService.update(transformDepartmentUtil.transformModelToDto(DEPARTMENT_1));

        int expectedNumberOfTheMethodInvocations = 1;

        then(departmentRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(DEPARTMENT_1);
    }

    @Test
    public void update_withNotExistConfiguration() {
        when(departmentRepository.findByDepartmentId(DEPARTMENT_ID_1)).thenReturn(Optional.empty());
        departmentService.update(transformDepartmentUtil.transformModelToDto(DEPARTMENT_1));
        int expectedNumberOfTheMethodInvocations = 0;

        then(departmentRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .save(DEPARTMENT_1);
    }

    @Test
    public void delete() {
        departmentService.delete(transformDepartmentUtil.transformModelToDto(DEPARTMENT_1));
        int expectedNumberOfTheMethodInvocations = 1;

        then(departmentRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .delete(DEPARTMENT_1);
    }

    @Test
    public void deleteByDepartmentId() {
        departmentService.deleteByDepartmentId(DEPARTMENT_ID_1);
        int expectedNumberOfTheMethodInvocations = 1;

        then(departmentRepository)
                .should(times(expectedNumberOfTheMethodInvocations))
                .deleteByDepartmentId(DEPARTMENT_ID_1);
    }
}
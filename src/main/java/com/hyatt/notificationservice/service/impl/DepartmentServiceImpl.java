package com.hyatt.notificationservice.service.impl;

import com.hyatt.notificationservice.dto.DepartmentDto;
import com.hyatt.notificationservice.exception.ResourceNotFoundException;
import com.hyatt.notificationservice.model.Department;
import com.hyatt.notificationservice.repository.DepartmentRepository;
import com.hyatt.notificationservice.service.DepartmentService;
import com.hyatt.notificationservice.util.TransformDepartmentUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private static final String RESOURCE_NAME = "department";
    private static final String FIELD_NAME_DEPARTMENT_ID = "departmentID";
    private static final String IGNORED_FIELD_ID = "id";

    private final DepartmentRepository departmentRepository;
    private final TransformDepartmentUtil transformDepartmentUtil;

    @Override
    public List<DepartmentDto> findAll() {
        return Lists.newArrayList(departmentRepository.findAll()).stream()
                .map(transformDepartmentUtil::transformModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto findByDepartmentId(String departmentId) {
        return transformDepartmentUtil.transformModelToDto(
                departmentRepository.findByDepartmentId(departmentId)
                        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, FIELD_NAME_DEPARTMENT_ID, departmentId))
        );
    }

    @Override
    public void save(DepartmentDto departmentDto) {
        log.info("New Department: {}", departmentDto);
        departmentRepository.save(transformDepartmentUtil.transformDtoToModel(departmentDto));
    }

    @Override
    public void update(DepartmentDto departmentDto) {
        log.info("Updating entity: {}", departmentDto.toString());
        departmentRepository.findByDepartmentId(departmentDto.getDepartmentId()).ifPresent(dbModel ->
                saveToDbUpdatedEntity(transformDepartmentUtil.transformDtoToModel(departmentDto), dbModel));
    }

    @Override
    public void delete(DepartmentDto departmentDto) {
        log.info("Department removed: {}", departmentDto);
        departmentRepository.delete(transformDepartmentUtil.transformDtoToModel(departmentDto));
    }

    @Override
    public void deleteByDepartmentId(String departmentId) {
        log.info("Deleting department: {}", departmentId);
        departmentRepository.deleteByDepartmentId(departmentId);
    }

    private void saveToDbUpdatedEntity(Department model, Department dbModel) {
        List<String> nullPropertyNames = getNullPropertyNames(model);
        nullPropertyNames.add(IGNORED_FIELD_ID);
        BeanUtils.copyProperties(model, dbModel, nullPropertyNames.toArray(new String[0]));
        departmentRepository.save(dbModel);
        log.info("Updated entity: {}", dbModel);
    }

    private List<String> getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .collect(Collectors.toList());
    }
}

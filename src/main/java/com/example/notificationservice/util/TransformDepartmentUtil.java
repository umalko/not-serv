package com.example.notificationservice.util;

import com.example.notificationservice.dto.DepartmentDto;
import com.example.notificationservice.model.Department;
import org.springframework.stereotype.Component;

@Component
public class TransformDepartmentUtil implements TransformBeanUtil<Department, DepartmentDto> {

    @Override
    public DepartmentDto transformModelToDto(Department model) {
        return new DepartmentDto(model.getDepartmentId(), model.getName());
    }

    @Override
    public Department transformDtoToModel(DepartmentDto dto) {
        return new Department(dto.getDepartmentId(), dto.getName());
    }
}

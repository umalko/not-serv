package com.hyatt.notificationservice.service;

import com.hyatt.notificationservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> findAll();

    DepartmentDto findByDepartmentId(String departmentId);

    void save(DepartmentDto departmentDto);

    void update(DepartmentDto departmentDto);

    void delete(DepartmentDto departmentDto);

    void deleteByDepartmentId(String departmentId);
}

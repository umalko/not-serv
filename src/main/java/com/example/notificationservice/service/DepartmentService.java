package com.example.notificationservice.service;

import com.example.notificationservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> findAll();

    DepartmentDto findByDepartmentId(String departmentId);

    void save(DepartmentDto departmentDto);

    void update(DepartmentDto departmentDto);

    void delete(DepartmentDto departmentDto);

    void deleteByDepartmentId(String departmentId);
}

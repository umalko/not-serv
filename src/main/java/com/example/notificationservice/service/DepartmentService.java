package com.example.notificationservice.service;

import com.example.notificationservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> findAllDepartments();

}

package com.example.notificationservice.controller;

import com.example.notificationservice.dto.DepartmentDto;
import com.example.notificationservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/all")
    public List<DepartmentDto> listDepartments() {
        return departmentService.findAllDepartments();
    }
}

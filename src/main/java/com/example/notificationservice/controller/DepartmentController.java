package com.example.notificationservice.controller;

import com.example.notificationservice.dto.DepartmentDto;
import com.example.notificationservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDto> listDepartments() {
        return departmentService.findAll();
    }

    @GetMapping("/id/{id}")
    public DepartmentDto findByDepartmentId(@PathVariable("id") String id) {
        return departmentService.findByDepartmentId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody DepartmentDto departmentDto) {
        departmentService.save(departmentDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody DepartmentDto departmentDto) {
        departmentService.update(departmentDto);
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        departmentService.deleteByDepartmentId(id);
    }
}

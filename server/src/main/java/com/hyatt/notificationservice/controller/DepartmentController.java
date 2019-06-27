package com.hyatt.notificationservice.controller;

import com.hyatt.notificationservice.dto.DepartmentDto;
import com.hyatt.notificationservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Department Controller contains CRUD operations
 */
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * Find all departments
     *
     * @return list of departments
     */
    @GetMapping
    public List<DepartmentDto> listDepartments() {
        return departmentService.findAll();
    }

    /**
     * Find department by id
     *
     * @param id department id
     * @return department dto. See {@link DepartmentDto}
     */
    @GetMapping("/id/{id}")
    public DepartmentDto findByDepartmentId(@PathVariable("id") String id) {
        return departmentService.findByDepartmentId(id);
    }

    /**
     * Creates department info.
     *
     * @param departmentDto department dto. See {@link DepartmentDto}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody DepartmentDto departmentDto) {
        departmentService.save(departmentDto);
    }

    /**
     * Updates department info
     *
     * @param departmentDto department dto. See {@link DepartmentDto}
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody DepartmentDto departmentDto) {
        departmentService.update(departmentDto);
    }

    /**
     * Deletes department by id
     *
     * @param id department id
     */
    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        departmentService.deleteByDepartmentId(id);
    }
}

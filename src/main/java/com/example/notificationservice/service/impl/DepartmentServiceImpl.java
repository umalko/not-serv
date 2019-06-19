package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.DepartmentDto;
import com.example.notificationservice.repository.DepartmentDao;
import com.example.notificationservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDao departmentDao;

    @Override
    public List<DepartmentDto> findAllDepartments() {
        return departmentDao.findAll().stream()
                .map(m -> new DepartmentDto(m.getId(), m.getName()))
                .collect(Collectors.toList());
    }
}

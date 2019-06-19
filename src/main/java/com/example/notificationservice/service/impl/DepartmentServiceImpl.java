package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.DepartmentDto;
import com.example.notificationservice.repository.DepartmentRepository;
import com.example.notificationservice.service.DepartmentService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> findAllDepartments() {
        return Lists.newArrayList(departmentRepository.findAll()).stream()
                .map(m -> new DepartmentDto(m.getDepartmentId(), m.getName()))
                .collect(Collectors.toList());
    }
}

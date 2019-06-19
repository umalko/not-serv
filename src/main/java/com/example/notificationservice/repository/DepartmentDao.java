package com.example.notificationservice.repository;

import com.example.notificationservice.model.Department;

import java.util.List;

public interface DepartmentDao {

    List<Department> findAll();
}

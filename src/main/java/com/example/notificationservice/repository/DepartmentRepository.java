package com.example.notificationservice.repository;

import com.example.notificationservice.model.Department;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {

    Optional<Department> findByDepartmentId(String departmentId);

    void deleteByDepartmentId(String departmentId);
}

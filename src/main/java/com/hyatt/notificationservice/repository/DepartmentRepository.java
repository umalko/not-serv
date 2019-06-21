package com.hyatt.notificationservice.repository;

import com.hyatt.notificationservice.model.Department;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {

    Optional<Department> findByDepartmentId(String departmentId);

    void deleteByDepartmentId(String departmentId);
}

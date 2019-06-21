package com.hyatt.notificationservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue
    private Long id;
    private String departmentId;
    private String name;

    public Department(String departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }
}

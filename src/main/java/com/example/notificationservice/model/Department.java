package com.example.notificationservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "departments")
public class Department {

    private String id;
    private String name;
}

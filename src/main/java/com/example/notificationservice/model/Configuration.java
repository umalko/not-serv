package com.example.notificationservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "configurations")
public class Configuration {

    @Id
    private String name;
    private String value;
}

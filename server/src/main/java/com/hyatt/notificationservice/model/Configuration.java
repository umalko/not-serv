package com.hyatt.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Configuration for th API. Basic key - value object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configurations")
public class Configuration {

    @Id
    private String name;
    private String value;
}

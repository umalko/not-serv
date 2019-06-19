package com.example.notificationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgresConfig {

    @Value("${PGURL:jdbc:postgresql://localhost:5432/notification-service-db}")
    private String url;

    @Value("${PGUSER:postgres}")
    private String username;

    @Value("${PGPASSWORD:root}")
    private String password;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

}

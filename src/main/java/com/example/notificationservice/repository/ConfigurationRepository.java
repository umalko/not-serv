package com.example.notificationservice.repository;

import com.example.notificationservice.model.Configuration;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends PagingAndSortingRepository<Configuration, String> {

}

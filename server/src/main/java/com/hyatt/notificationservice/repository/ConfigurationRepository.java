package com.hyatt.notificationservice.repository;

import com.hyatt.notificationservice.model.Configuration;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Configuration Repository contains CRUD operations with DB layer
 */
@Repository
public interface ConfigurationRepository extends PagingAndSortingRepository<Configuration, String> {

}

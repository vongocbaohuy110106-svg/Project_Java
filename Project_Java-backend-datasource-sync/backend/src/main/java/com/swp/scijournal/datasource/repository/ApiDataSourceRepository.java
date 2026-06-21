package com.swp.scijournal.datasource.repository;

import com.swp.scijournal.datasource.entity.ApiDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ApiDataSourceRepository extends JpaRepository<ApiDataSource, Long> {
    Optional<ApiDataSource> findBySourceName(String sourceName);
}
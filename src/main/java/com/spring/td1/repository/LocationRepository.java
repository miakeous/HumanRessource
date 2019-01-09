package com.spring.td1.repository;

import com.spring.td1.model.LocationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationsEntity, Long> {

    List<LocationsEntity> findAll();
}

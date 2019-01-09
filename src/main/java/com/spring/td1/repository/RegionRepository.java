package com.spring.td1.repository;

import com.spring.td1.model.CountriesEntity;
import com.spring.td1.model.RegionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface RegionRepository extends JpaRepository<RegionsEntity, Long> {

    List<RegionsEntity> findAll();
    RegionsEntity findDistinctByRegionId(int regionId);

}

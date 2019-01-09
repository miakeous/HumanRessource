package com.spring.td1.repository;


import com.spring.td1.model.CountriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface CountryRepository extends JpaRepository<CountriesEntity, Long> {

    List<CountriesEntity> findAll();
    CountriesEntity findDistinctByCountryId(String countryId);
}
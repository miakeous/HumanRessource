package com.spring.td1.repository;

import com.spring.td1.model.DepartmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentsEntity, Long> {

    List<DepartmentsEntity> findAll();
    DepartmentsEntity findDistinctByDepartmentName(String departmentName);
}

package com.spring.td1.repository;

import com.spring.td1.model.EmployeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface EmployeesRepository extends JpaRepository<EmployeesEntity, Long> {

    List<EmployeesEntity> findAllByFirstName(String firstName);
    EmployeesEntity findEmployeesEntitiesByFirstName(String firstName);
    EmployeesEntity findEmployeesEntitiesByEmail(String email);

    EmployeesEntity findEmployeesEntitiesByEmployeeId(int employeeId);
}

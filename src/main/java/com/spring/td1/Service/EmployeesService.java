package com.spring.td1.Service;

import com.spring.td1.model.EmployeesEntity;
import com.spring.td1.model.EmployeesEntityDTO;
import com.spring.td1.model.JobsEntity;
import com.spring.td1.repository.EmployeesRepository;
import com.spring.td1.repository.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeesService implements UserDetailsService {
    @Autowired
    private  EmployeesRepository employeesRepository;
    private JobsRepository jobsRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public EmployeesEntityDTO findEmployeeByFirstname(String firstName) {
        return new EmployeesEntityDTO(employeesRepository.findEmployeesEntitiesByFirstName(firstName));
    }

    public EmployeesEntityDTO findEmployeeByEmail(String email) {
        return new EmployeesEntityDTO(employeesRepository.findEmployeesEntitiesByEmail(email));
    }



    public void saveEmployees(EmployeesEntity employeesEntity,String jobId) {
        employeesEntity.setPassword(bCryptPasswordEncoder.encode(employeesEntity.getPassword()));
        JobsEntity employeeJob = jobsRepository.findOneByJobId(jobId);
        employeesEntity.setJobId(employeeJob);
        employeesRepository.save(employeesEntity);
    }


    public EmployeesEntityDTO loadUserByUsername(String username)  {
        Objects.requireNonNull(username);
        EmployeesEntity user = employeesRepository.findEmployeesEntitiesByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new EmployeesEntityDTO(user);
    }

    public List<EmployeesEntityDTO> findAll(){
        List<EmployeesEntity> test = employeesRepository.findAll();
        List<EmployeesEntityDTO> res = new ArrayList<EmployeesEntityDTO>();
        for (EmployeesEntity e: test
             ) {
            res.add(new EmployeesEntityDTO(e));
        }

        return res;
    }
}
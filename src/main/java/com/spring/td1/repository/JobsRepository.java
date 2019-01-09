package com.spring.td1.repository;

import com.spring.td1.model.CountriesEntity;
import com.spring.td1.model.JobsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobsRepository extends CrudRepository<JobsEntity, Long> {

    JobsEntity findOneByJobId(String jobId);

    @Query(nativeQuery = true, value= "SELECT * FROM jobs j where j.MIN_SALARY > :minSal order by j.MAX_SALARY Desc")
    List<JobsEntity> getJobs(@Param("minSal") Integer minSal);


    JobsEntity findJobsEntitiesByJobId(String jobId);
    List<JobsEntity> findAll();

}
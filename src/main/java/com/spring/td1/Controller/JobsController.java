package com.spring.td1.Controller;

import com.spring.td1.model.CountriesEntity;
import com.spring.td1.model.JobsEntity;
import com.spring.td1.repository.CountryRepository;
import com.spring.td1.repository.JobsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class JobsController {

    @Autowired
    private JobsRepository jobsRepository;

    private static final Logger logger = LoggerFactory.getLogger(JobsController.class);

    @RequestMapping(path="/job", method = RequestMethod.POST)
    @ResponseBody
    public void getCountry (@RequestParam String jobId, @RequestParam String jobName) {
         JobsEntity newjob = this.jobsRepository.findOneByJobId(jobId);
         newjob.setJobTitle(jobName);
         this.jobsRepository.save(newjob);

    }

    @RequestMapping(path="/alljob")
    public @ResponseBody Iterable<JobsEntity>  allJob () {
        return this.jobsRepository.findAll();
    }

    @RequestMapping(path="/getjob")
    public @ResponseBody Iterable<JobsEntity>  getAllJob (@RequestParam Integer minSal) {
        return this.jobsRepository.getJobs(minSal);
    }



}

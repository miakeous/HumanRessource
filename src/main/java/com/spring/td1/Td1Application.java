package com.spring.td1;

import com.spring.td1.Service.EmployeesService;
import com.spring.td1.model.EmployeesEntity;
import com.spring.td1.repository.EmployeesRepository;
import com.spring.td1.repository.JobsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class Td1Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Td1Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Td1Application.class, args);
	}


	@Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void run(String... args) throws Exception {
        EmployeesEntity employeesEntityAdmin = employeesRepository.findEmployeesEntitiesByEmail("SKING");
        logger.info("On EXECUTE LE COMMANDLINERUNNER"+employeesEntityAdmin.getFirstName());
        employeesEntityAdmin.setPassword(bCryptPasswordEncoder.encode("azertyuiop"));

        employeesRepository.save(employeesEntityAdmin);

        EmployeesEntity employeesEntityAdmin2 = employeesRepository.findEmployeesEntitiesByEmail("BERNST");
        logger.info("On EXECUTE LE COMMANDLINERUNNER"+employeesEntityAdmin2.getFirstName());
        employeesEntityAdmin2.setPassword(bCryptPasswordEncoder.encode("azertyuiop"));

        employeesRepository.save(employeesEntityAdmin2);




	}
}

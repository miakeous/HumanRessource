package com.spring.td1.Controller;


import com.spring.td1.Service.EmployeesService;
import com.spring.td1.model.EmployeesEntityDTO;
import com.spring.td1.model.LocationsEntity;
import com.spring.td1.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class LocationController {


    @Autowired
    private LocationRepository locationRepository;


    @Autowired
    private EmployeesService employeesService;

    @RequestMapping(path="/locations",method = RequestMethod.GET)
    public String allLocation(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        List<LocationsEntity> locationsEntityList = this.locationRepository.findAll();
        model.addAttribute("locationList",locationsEntityList);

        return "location";
    }


}

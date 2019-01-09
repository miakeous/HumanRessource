package com.spring.td1.Controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.td1.Service.EmployeesService;
import com.spring.td1.model.EmployeesEntity;
import com.spring.td1.model.EmployeesEntityDTO;
import com.spring.td1.repository.DepartmentRepository;
import com.spring.td1.repository.EmployeesRepository;
import com.spring.td1.repository.JobsRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EmployeesController {

    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobsRepository jobsRepository;


    private static final Logger logger = LoggerFactory.getLogger(JobsController.class);
    @RequestMapping(path="/employees")
    public @ResponseBody Iterable<EmployeesEntityDTO>  employees (@RequestParam String firstName) {
        List<EmployeesEntity> test  = this.employeesRepository.findAllByFirstName(firstName);

        ModelMapper modelMapper = new ModelMapper();
        java.lang.reflect.Type targetListType = new TypeToken<List<EmployeesEntityDTO>>() {}.getType();
        List<EmployeesEntityDTO> res = modelMapper.map(test, targetListType);
        return res;
    }

    @RequestMapping(path="/allemployees")
    public String allEmployees (Model model) {
        List<EmployeesEntityDTO> res  = this.employeesService.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("USER SUR ALLEMPLOYEES "+ auth.getName());
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());

        model.addAttribute("users",res);


        return "allEmployees";
    }

    @JsonSerialize
    @RequestMapping(path="/employeesGraph",produces= "application/json")
    public String employeesGraph (Model model) {
        List<EmployeesEntityDTO> res  = this.employeesService.findAll();
        res.sort(Comparator.comparing(EmployeesEntityDTO::getSalary));
        Collections.reverse(res);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("USER SUR ALLEMPLOYEES "+ auth.getName());
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        JSONObject jsonFinal = new JSONObject();
        JSONObject json = new JSONObject();
        JSONArray json2 = new JSONArray();
        JSONArray json3 = new JSONArray();
        List<String> axeX;
        List<Integer> axeY;
        axeX = res.stream().map(i-> i.getName()).collect(Collectors.toList());

        axeY = res.stream().map(i-> i.getSalary()).collect(Collectors.toList());
        logger.info("AXEX " , axeX);

        for (String e: axeX
             ) {
            json3.put(e);
        }
        for (int e: axeY
        ) {
            json2.put(e);
        }
        model.addAttribute("axeX",json3);
        model.addAttribute("axeY",json2);




        return "employees";
    }


    @RequestMapping(path="/employeesUpdate")
    public String employeesUpdate (Model model) {
        List<EmployeesEntityDTO> res  = this.employeesService.findAll();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("USER SUR ALLEMPLOYEES "+ auth.getName());
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());

        model.addAttribute("users",res);


        return "employeesUpdate";
    }

    @RequestMapping(path="/update/employees/{employeesId}",method = RequestMethod.GET)
    public String updateEmployeesData(Model model, @PathVariable String employeesId){
      EmployeesEntity updateEmployees = new EmployeesEntity();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        EmployeesEntity employees = this.employeesRepository.findEmployeesEntitiesByEmployeeId(Integer.valueOf(employeesId));
        model.addAttribute("employeesForm",employees);
        model.addAttribute("department",departmentRepository.findAll());
        model.addAttribute("jobs",jobsRepository.findAll());

        return "employeesForm";
    }

    @RequestMapping(value = { "/update/employees" }, method = RequestMethod.POST)
    public ModelAndView updateEmployeeInformation(Model model, //
                                                @ModelAttribute("employeesForm") EmployeesEntity employeesEntity) {

        String depart = employeesEntity.getDepartmentIdString();
        String job = employeesEntity.getJobIdString();
        employeesEntity.setDepartmentId(departmentRepository.findDistinctByDepartmentName(depart));
        employeesEntity.setJobId(jobsRepository.findOneByJobId(job));
        employeesRepository.save(employeesEntity);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        ModelAndView r = new ModelAndView();
        model.addAttribute("name",user.getUsername());
        r.setViewName("home");
        return r;

    }



    @JsonSerialize
    @RequestMapping(path="/employeesDecile",produces= "application/json")
    public String employeesDecil (Model model) {
        List<EmployeesEntityDTO> res  = this.employeesService.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("On accède au graphique décile : "+ auth.getName());
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        res.sort(Comparator.comparing(EmployeesEntityDTO::getSalary));
        Collections.reverse(res);
        JSONArray json2 = new JSONArray();
        JSONArray json3 = new JSONArray();
        List<Integer> axeX = new ArrayList<>();
        List<Integer> axeY = new ArrayList<>();
        int nbreSalarie = res.size()-1;
        int maxSal = res.stream()
                .map(EmployeesEntityDTO::getSalary)
                .max(Comparator.comparing(Integer::valueOf))
                .get();

        int minSal = res.stream()
                .map(EmployeesEntityDTO::getSalary)
                .min(Comparator.comparing(Integer::valueOf))
                .get();
        int ecart = (maxSal-minSal)/10;
        axeX.add(maxSal);
        for(int o =1; o<10; o++){
         axeX.add(maxSal-o*ecart);
        }
        int j = 0;
        int i = 1;
        System.out.println(ecart);
        for (EmployeesEntityDTO e: res
             ) {
            System.out.println(e.getSalary());
            if(e.getSalary() <= axeX.get(i) ){
                if(i<=8){
                    i++;
                    axeY.add((j));
                }else{
                    axeY.add(nbreSalarie- (j));
                }


            }
            j++;
        }
        for (Integer e: axeX
        ) {
            json3.put(e);
        }
        for (Integer e: axeY
        ) {
            json2.put(e);
        }
        model.addAttribute("axeX",json3);
        model.addAttribute("axeY",json2);
        return "employeeDecile";
    }


}

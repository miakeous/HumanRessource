package com.spring.td1.Controller;


import com.spring.td1.Service.EmployeesService;
import com.spring.td1.model.CountriesEntity;
import com.spring.td1.model.CountriesForm;
import com.spring.td1.model.EmployeesEntityDTO;
import com.spring.td1.model.RegionsEntity;
import com.spring.td1.repository.CountryRepository;
import com.spring.td1.repository.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class CoutriesController {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private EmployeesService employeesService;


    private static final Logger logger = LoggerFactory.getLogger(CoutriesController.class);
    @RequestMapping(path="/country")
    public @ResponseBody Iterable<CountriesEntity> getCountry () {
        return this.countryRepository.findAll();
    }


    @RequestMapping(path="/countries",method = RequestMethod.GET)
    public String allCountries(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        List<CountriesEntity> countries = this.countryRepository.findAll();
        model.addAttribute("countriesList",countries);
        return "coutries";
    }

    @RequestMapping(path="/countriesUpdate",method = RequestMethod.GET)
    public String countriesUpdate(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        logger.info("USER DANS COUNTRY UP " + auth.getAuthorities().toString());
        List<CountriesEntity> countries = this.countryRepository.findAll();
        model.addAttribute("countriesList",countries);
        logger.info("USER DANS COUNTRY UPDATE " + user.getRole());
        return "coutriesUpdate";
    }


    @RequestMapping(path="/update/country/{countryId}",method = RequestMethod.GET)
    public String updateCountries(Model model, @PathVariable String countryId){
        CountriesForm form = new CountriesForm();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        CountriesEntity pays = this.countryRepository.findDistinctByCountryId(countryId);
        form.setCountryId(pays.getCountryId());
        form.setCountryName(pays.getCountryName());
        model.addAttribute("countriesForm",form);

        return "countryForm";
    }


    @RequestMapping(value = { "/update/country" }, method = RequestMethod.POST)
    public ModelAndView updateCountryInformation(Model model, //
                                                 @ModelAttribute("countriesForm") CountriesForm countriesForm) {
        String countriesName = countriesForm.getCountryName();
        String countriesId = countriesForm.getCountryId();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        if (countriesName != null && countriesName.length() > 0 //
                && countriesId != null && countriesId.length() > 0) {
            CountriesEntity newCoutry =this.countryRepository.findDistinctByCountryId(countriesId);
            newCoutry.setCountryName(countriesName);
            this.countryRepository.save(newCoutry);
        }
        ModelAndView r = new ModelAndView();
        r.setViewName("home");
      return r;
    }






}

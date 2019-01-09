package com.spring.td1.Controller;

import com.spring.td1.Service.EmployeesService;
import com.spring.td1.model.EmployeesEntityDTO;
import com.spring.td1.model.RegionForm;
import com.spring.td1.model.RegionsEntity;
import com.spring.td1.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;


    @Autowired
    private EmployeesService employeesService;


    @RequestMapping(path="/regions",method = RequestMethod.GET)
    public String allRegion(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        List<RegionsEntity> regionsEntityList = this.regionRepository.findAll();
        model.addAttribute("regionList",regionsEntityList);
        return "regions";
    }

    @RequestMapping(path="/update/region/{regionId}",method = RequestMethod.GET)
    public String updateRegion(Model model, @PathVariable int regionId){
        RegionForm form = new RegionForm();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        RegionsEntity pays = this.regionRepository.findDistinctByRegionId(regionId);
        form.setRegionId(pays.getRegionId());
        form.setRegionName(pays.getRegionName());
        model.addAttribute("regionsForm",form);
        return "regionForm";
    }

    @RequestMapping(value = { "/update/region" }, method = RequestMethod.POST)
    public ModelAndView updateRegionInformation(Model model, //
                                                @ModelAttribute("regionsForm") RegionForm regionForm) {

        String regionName = regionForm.getRegionName();
        int regionId = regionForm.getRegionId();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        if (regionName.length() > 0) {
            RegionsEntity newRegions =this.regionRepository.findDistinctByRegionId(regionId);
            newRegions.setRegionName(regionName);
            this.regionRepository.save(newRegions);
        }
        ModelAndView r = new ModelAndView();
        model.addAttribute("name",user.getUsername());
        r.setViewName("home");
        return r;

    }

    @RequestMapping(path="/regionsUpdate",method = RequestMethod.GET)
    public String regionUpdate(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        List<RegionsEntity> regionsEntityList = this.regionRepository.findAll();
        model.addAttribute("regionsList",regionsEntityList);
        return "regionsUpdate";
    }

}

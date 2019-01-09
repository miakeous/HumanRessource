package com.spring.td1.Controller;

import com.spring.td1.Service.EmployeesService;
import com.spring.td1.model.EmployeesEntity;
import com.spring.td1.model.EmployeesEntityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private EmployeesService employeesService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        EmployeesEntity user = new EmployeesEntity();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }
    @Transactional
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid EmployeesEntity user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        EmployeesEntityDTO userExists = employeesService.findEmployeeByFirstname(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            employeesService.saveEmployees(user,user.getJobId().getJobId());
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new EmployeesEntity());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView adminHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }


    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String home(Model model){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeesEntityDTO user = employeesService.findEmployeeByEmail(auth.getName());
        logger.info("LOGIN SUCCESSFULL REDIRECTION TO HOME");
        model.addAttribute("role","USER");
        model.addAttribute("name",user.getUsername());
        return "home";
    }

}

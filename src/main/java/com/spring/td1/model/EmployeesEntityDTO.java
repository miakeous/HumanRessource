package com.spring.td1.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeesEntityDTO  implements UserDetails {
    public EmployeesEntity user;



    public EmployeesEntityDTO(EmployeesEntity user) {
        this.user= user;
    }

    @Override
    public String toString() {
        return "EmployeesEntityDTO{" +
                "firstName='" + user.getFirstName() + '\'' +
                ", lastName='" + user.getLastName() + '\'' +
                ", departmentName=" + user.getDepartmentId().getDepartmentName()+
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user.getJobId().getJobId().equals("AD_PRES")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN" ));
        }

        if(user.getJobId().getJobId().equals("FI_ACCOUNT") ||
                user.getJobId().getJobId().equals("FI_MGR") ||
                user.getJobId().getJobId().equals("AC_ACCOUNT") ||
                user.getJobId().getJobId().equals("AC_MGR") ){

            authorities.add(new SimpleGrantedAuthority("ROLE_RH" ));
        }

        if(user.getJobId().getJobId().equals("SA_REP") ||
                user.getJobId().getJobId().equals("SA_MAN")
                ){

            authorities.add(new SimpleGrantedAuthority("ROLE_SALES" ));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER" ));

        return authorities;

    }

    public int getSalary(){
        return  user.getSalary().intValue();
    }


    public String getDepartement(){
       return  user.getDepartmentId().getDepartmentName();
    }

    public String getPhone(){
        return  user.getPhoneNumber();
    }

    public String getAddress(){
        return user.getDepartmentId().getLocationId().getCity()+" "+ user.getDepartmentId().getLocationId().getStreetAddress();
    }

    public String getJobHistory(){
        List<JobHistoryEntity> res = user.getJobhistoryList();
        String rep = "";
        for (JobHistoryEntity e: res
             ) {
            rep = rep + e.getJobId().getJobTitle() +"; ";
        }
        return rep;
    }
public int getId(){
        return user.getEmployeeId();
}
    public String getRole() {
        return user.getJobId().getJobId() ;
    }
    public String getPoste() {
        return user.getJobId().getJobTitle() ;
    }
    public String getName() {
        return user.getLastName() + " " + user.getFirstName() ;
    }
    @Override
    public String getPassword() {
        return user.getPassword() ;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

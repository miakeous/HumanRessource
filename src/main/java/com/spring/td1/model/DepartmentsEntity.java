package com.spring.td1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "departments", schema = "humanresources", catalog = "")
public class DepartmentsEntity {
    private Integer departmentId;
    private String departmentName;
    private Integer managerId;
    private LocationsEntity locationId;
    @JsonIgnore
    private List<EmployeesEntity> employeesList;
    @JsonIgnore
    private List<JobHistoryEntity> jobhistoryList;

    @Id
    @Column(name = "DEPARTMENT_ID")
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "DEPARTMENT_NAME")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "MANAGER_ID")
    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentsEntity that = (DepartmentsEntity) o;
        return Objects.equals(departmentId, that.departmentId) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(managerId, that.managerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, departmentName, managerId);
    }

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID")
    public LocationsEntity getLocationId() {
        return locationId;
    }

    public void setLocationId(LocationsEntity locationId) {
        this.locationId = locationId;
    }

    @OneToMany(mappedBy = "departmentId")
    public List<EmployeesEntity> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<EmployeesEntity> employeesList) {
        this.employeesList = employeesList;
    }

    @OneToMany(mappedBy = "departmentId")
    public List<JobHistoryEntity> getJobhistoryList() {
        return jobhistoryList;
    }

    public void setJobhistoryList(List<JobHistoryEntity> jobhistoryList) {
        this.jobhistoryList = jobhistoryList;
    }
}

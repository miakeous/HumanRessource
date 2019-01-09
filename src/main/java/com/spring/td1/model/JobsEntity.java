package com.spring.td1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "jobs", schema = "humanresources", catalog = "")
public class JobsEntity {
    private String jobId;
    private String jobTitle;
    private Integer minSalary;
    private Integer maxSalary;
    @JsonIgnore
    private List<EmployeesEntity> employeesList;
    @JsonIgnore
    private List<JobHistoryEntity> jobhistoryList;

    @Id
    @Column(name = "JOB_ID")
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "JOB_TITLE")
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Basic
    @Column(name = "MIN_SALARY")
    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    @Basic
    @Column(name = "MAX_SALARY")
    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobsEntity that = (JobsEntity) o;
        return Objects.equals(jobId, that.jobId) &&
                Objects.equals(jobTitle, that.jobTitle) &&
                Objects.equals(minSalary, that.minSalary) &&
                Objects.equals(maxSalary, that.maxSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, jobTitle, minSalary, maxSalary);
    }

    @OneToMany(mappedBy = "jobId")
    public List<EmployeesEntity> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<EmployeesEntity> employeesList) {
        this.employeesList = employeesList;
    }

    @OneToMany(mappedBy = "jobId")
    public List<JobHistoryEntity> getJobhistoryList() {
        return jobhistoryList;
    }

    public void setJobhistoryList(List<JobHistoryEntity> jobhistoryList) {
        this.jobhistoryList = jobhistoryList;
    }
}

package com.spring.td1.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "job_history", schema = "humanresources", catalog = "")
public class JobHistoryEntity {
    private Date startDate;
    private Date endDate;
    private DepartmentsEntity departmentId;
    private EmployeesEntity employeeId;
    private JobsEntity jobId;

    @Id
    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobHistoryEntity that = (JobHistoryEntity) o;
        return Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID")
    public DepartmentsEntity getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(DepartmentsEntity departmentId) {
        this.departmentId = departmentId;
    }

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID", nullable = false)
    public EmployeesEntity getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeesEntity employeeId) {
        this.employeeId = employeeId;
    }

    @ManyToOne
    @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID", nullable = false)
    public JobsEntity getJobId() {
        return jobId;
    }

    public void setJobId(JobsEntity jobId) {
        this.jobId = jobId;
    }
}

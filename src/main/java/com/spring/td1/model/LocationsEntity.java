package com.spring.td1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "locations", schema = "humanresources")
public class LocationsEntity {
    private Integer locationId;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
    @JsonIgnore
    private List<DepartmentsEntity> departmentsList;
    private CountriesEntity countryId;

    @Id
    @Column(name = "LOCATION_ID")
    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "STREET_ADDRESS")
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Basic
    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "STATE_PROVINCE")
    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationsEntity that = (LocationsEntity) o;
        return Objects.equals(locationId, that.locationId) &&
                Objects.equals(streetAddress, that.streetAddress) &&
                Objects.equals(postalCode, that.postalCode) &&
                Objects.equals(city, that.city) &&
                Objects.equals(stateProvince, that.stateProvince);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, streetAddress, postalCode, city, stateProvince);
    }

    @OneToMany(mappedBy = "locationId")
    public List<DepartmentsEntity> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<DepartmentsEntity> departmentsList) {
        this.departmentsList = departmentsList;
    }

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID")
    public CountriesEntity getCountryId() {
        return countryId;
    }

    public void setCountryId(CountriesEntity countryId) {
        this.countryId = countryId;
    }
}

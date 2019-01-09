package com.spring.td1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "countries", schema = "humanresources")
public class CountriesEntity {
    private String countryId;
    private String countryName;
    private RegionsEntity regionId;
    @JsonIgnore
    private List<LocationsEntity> locationsList;

    @Id
    @Column(name = "COUNTRY_ID")
    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "COUNTRY_NAME")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountriesEntity that = (CountriesEntity) o;
        return Objects.equals(countryId, that.countryId) &&
                Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, countryName);
    }

    @ManyToOne
    @JoinColumn(name = "REGION_ID", referencedColumnName = "REGION_ID")
    public RegionsEntity getRegionId() {
        return regionId;
    }

    public void setRegionId(RegionsEntity regionId) {
        this.regionId = regionId;
    }

    @OneToMany(mappedBy = "countryId")
    public List<LocationsEntity> getLocationsList() {
        return locationsList;
    }

    public void setLocationsList(List<LocationsEntity> locationsList) {
        this.locationsList = locationsList;
    }
}

package com.spring.td1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "regions", schema = "humanresources", catalog = "")
public class RegionsEntity {
    private Integer regionId;
    private String regionName;
    @JsonIgnore
    private List<CountriesEntity> countriesList;

    @Id
    @Column(name = "REGION_ID")
    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "REGION_NAME")
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionsEntity that = (RegionsEntity) o;
        return Objects.equals(regionId, that.regionId) &&
                Objects.equals(regionName, that.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, regionName);
    }

    @OneToMany(mappedBy = "regionId")
    public List<CountriesEntity> getCountriesList() {
        return countriesList;
    }

    public void setCountriesList(List<CountriesEntity> countriesList) {
        this.countriesList = countriesList;
    }
}

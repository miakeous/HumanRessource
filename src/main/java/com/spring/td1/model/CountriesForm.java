package com.spring.td1.model;

import java.util.List;

public class CountriesForm {

    private String countryId;
    private String countryName;
    private String regionId;

    public List<String> getRegionsList() {
        return regionsList;
    }

    public void setRegionsList(List<String> regionsList) {
        this.regionsList = regionsList;
    }

    private List<String> regionsList;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}

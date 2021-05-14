package com.hamiliserver.sampleapp.model;

import java.util.Date;

public class Holiday {

    private Date date;
    private String localName;
    private String name;
    private String countryCode;
    private Boolean fixed;
    private Boolean global;
    private String countries;
    private Integer launchYear;
    private String type;

    public Holiday() {
    }

    public Holiday(Date date, String localName, String name, String countryCode, Boolean fixed, Boolean global, String countries, Integer launchYear, String type) {
        this.date = date;
        this.localName = localName;
        this.name = name;
        this.countryCode = countryCode;
        this.fixed = fixed;
        this.global = global;
        this.countries = countries;
        this.launchYear = launchYear;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public Integer getLaunchYear() {
        return launchYear;
    }

    public void setLaunchYear(Integer launchYear) {
        this.launchYear = launchYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /*{
        "date":"2017-01-01",
            "localName":"Neujahr",
            "name":"New Year's Day",
            "countryCode":"AT",
            "fixed":true,
            "global":true,
            "counties":null,
            "launchYear":1967,
            "type":"Public"
    }*/

}

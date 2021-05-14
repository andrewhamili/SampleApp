package com.hamiliserver.sampleapp.model;

import com.activeandroid.annotation.Column;

import java.util.Date;

public class Team {

    private String country;

    private String name;

    private String employeeId;

    private Date dateEntry;

    public Team() {
    }

    public Team(String country, String name, String employeeId, Date dateEntry) {
        this.country = country;
        this.name = name;
        this.employeeId = employeeId;
        this.dateEntry = dateEntry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(Date dateEntry) {
        this.dateEntry = dateEntry;
    }
}

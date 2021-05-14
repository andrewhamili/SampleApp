package com.hamiliserver.sampleapp.ActiveAndroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "Team")
public class Team extends Model {

    @Column(name = "country")
    public String country;
    @Column(name = "name")
    public String name;
    @Column(name = "employeeId")
    public String employeeId;
    @Column(name = "dateEntry")
    public Date dateEntry;

    public Team() {
    }

    public Team(String country, String name, String employeeId) {
        this.country = country;
        this.name = name;
        this.employeeId = employeeId;
        this.dateEntry = new Date();
    }

    public static List<Team> getAll() {
        return new Select()
                .from(Team.class)
                .execute();
    }

    public static Team getById(Long id) {
        return new Select()
                .from(Team.class)
                .where("id = ? ", id)
                .executeSingle();
    }

    public static void insert(List<Team> teams) {
        for (Team team : teams) {
            Team item = new Team();
            item.setCountry(team.getCountry());
            item.setName(team.getName());
            item.setEmployeeId(team.getEmployeeId());

            item.save();

        }
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
}

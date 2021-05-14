package com.hamiliserver.sampleapp.ActiveAndroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "Schedule")
public class Schedule extends Model {

    @Column(name = "teamId")
    private Long teamId;
    @Column(name = "scheduleDate")
    private Date scheduleDate;
    @Column(name = "title")
    private String title;
    @Column(name = "details")
    private String details;
    @Column(name = "dateEntry")
    private Date dateEntry;

    public Schedule() {
    }

    public Schedule(Long teamId, Date scheduleDate, String title, String details) {
        this.teamId = teamId;
        this.scheduleDate = scheduleDate;
        this.title = title;
        this.details = details;
        this.dateEntry = new Date();
    }

    public static List<Schedule> get() {
        return new Select()
                .from(Schedule.class)
                .orderBy("scheduleDate DESC")
                .execute();
    }

    public static List<Schedule> getByTeamId(Long id) {
        return new Select()
                .from(Schedule.class)
                .where("teamId = ?", id)
                .execute();
    }

    public static void insert(Schedule schedule) {
        schedule.save();

    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(Date dateEntry) {
        this.dateEntry = dateEntry;
    }
}

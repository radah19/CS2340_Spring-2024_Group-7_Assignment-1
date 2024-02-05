package com.example.cs_2340_project1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AssignmentModel {
    boolean isComplete;
    String location;
    String title;
    String courseName;
    LocalDate date;
    LocalTime time;

    public AssignmentModel(String location, String title, String courseName, LocalDate date, LocalTime time, boolean isComplete) {
        this.location = location;
        this.title = title;
        this.courseName = courseName;
        this.date = date;
        this.time = time;
        this.isComplete = isComplete;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}

package com.example.cs_2340_project1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AssignmentModel {
    private boolean isComplete;

    private String title;
    private LocalDate date;
    private LocalTime time;

    enum itemTypes {
        LIST_ITEM, ASSIGNMENT, EXAM
    }
    private int itemType;
    private String location;
    private String courseName;

    public AssignmentModel(String title, LocalDate date, LocalTime time, int itemType, String location, String courseName, boolean isComplete) {
        this.isComplete = isComplete;
        this.title = title;
        this.date = date;
        this.time = time;
        this.itemType = itemType;
        this.location = location;
        this.courseName = courseName;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}

package com.example.cs_2340_project1;

public class ClassesModel {
    String location;
    String section;
    String professor;
    String courseName;
    String dateAndtime;
    String repeat;
    public ClassesModel(String location, String section, String professor, String courseName, String dateAndtime, String repeat) {
        this.location = location;
        this.section = section;
        this.professor = professor;
        this.courseName = courseName;
        this.dateAndtime = dateAndtime;
        this.repeat = repeat;
    }

    public String getLocation() {
        return location;
    }

    public String getSection() {
        return section;
    }

    public String getProfessor() {
        return professor;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDateAndtime() {
        return dateAndtime;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public void setDateAndtime(String dateAndtime) {
        this.dateAndtime = dateAndtime;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}

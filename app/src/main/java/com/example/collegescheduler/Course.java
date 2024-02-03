package com.example.collegescheduler;

import java.io.Serializable;
import java.text.ParseException;

public class Course extends ActionItem implements Serializable {
    private String days;
    private String prof;
    private String location; // includes room number

    public Course(String title, String date, String days, String courseCode, String section, String prof, String location) throws ParseException {
        super(title, date, courseCode + " " + section);
        this.days = days;
        this.prof = prof;
        this.location = location;
    }
}

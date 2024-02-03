package com.example.collegescheduler;

import java.io.Serializable;
import java.text.ParseException;

public class Exam extends TodoItem implements Serializable {
    private String location;

    public Exam (String name, String date, String course, String location) throws ParseException {
        super(name, date, course, false);
        this.location = location;
    }
}

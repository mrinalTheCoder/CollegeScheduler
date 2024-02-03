package com.example.collegescheduler;

import java.io.Serializable;
import java.text.ParseException;

public class Assignment extends TodoItem implements Serializable {
    public Assignment(String title, String date, String course) throws ParseException {
        super(title, date, course);
    }
}

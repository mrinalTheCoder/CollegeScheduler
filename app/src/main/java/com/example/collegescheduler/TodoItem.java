package com.example.collegescheduler;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class TodoItem extends ActionItem implements Serializable {
    private boolean complete;
    private final boolean isAssignment;

    public TodoItem(String title, String date, String course, boolean isAssignment) throws ParseException {
        super(title, date, course);
        this.complete = false;
        this.isAssignment = isAssignment;
    }
}
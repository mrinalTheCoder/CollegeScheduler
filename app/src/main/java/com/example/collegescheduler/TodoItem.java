package com.example.collegescheduler;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class TodoItem extends ActionItem implements Serializable {
    private boolean complete;

    public TodoItem(String title, String date, String course) throws ParseException {
        super(title, date, course);
        complete = false;
    }
}
package com.example.collegescheduler;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActionItem {
    private String title;
    private Date date;
    private String course;

    public ActionItem(String title, String date,String course) throws ParseException {
        this.title = title;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd-HHmm");
        this.date = df.parse(date);
        this.course = course;
    }

    public String getTitle() {
        return title;
    }
}

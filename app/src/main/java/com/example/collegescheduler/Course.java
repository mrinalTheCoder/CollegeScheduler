package com.example.collegescheduler;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.ParseException;

public class Course extends ActionItem implements Parcelable {
    private String days;
    private String prof;
    private String location; // includes room number

    public Course(String title, String date, String days, String courseCode, String section, String prof, String location) throws ParseException {
        super(title, date, courseCode + " " + section, Items.COURSE);
        this.days = days;
        this.prof = prof;
        this.location = location;
    }

    protected Course(Parcel in) {
        super(in);
        days = in.readString();
        prof = in.readString();
        location = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(days);
        dest.writeString(prof);
        dest.writeString(location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}

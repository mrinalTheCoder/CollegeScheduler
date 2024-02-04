package com.example.collegescheduler;

import android.os.Parcel;
import android.os.Parcelable;

public class Exam extends TodoItem implements Parcelable {
    private String location;

    public Exam (String name, String date, String course, String location) {
        super(name, date, course, false, Items.EXAM);
        this.location = location;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(location);
    }

    public String getLocation() {
        return this.location;
    }
}

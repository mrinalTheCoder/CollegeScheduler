package com.example.collegescheduler;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ActionItem implements Parcelable {
    private String title;
    private String date;
    private String course;

    public ActionItem(String title, String date,String course) {
        this.title = title;
        this.date = date;
        this.course = course;
    }

    public ActionItem(Parcel in) {
        title = in.readString();
        date = in.readString();
        course = in.readString();
    }

    public static final Creator<ActionItem> CREATOR = new Creator<ActionItem>() {
        @Override
        public ActionItem createFromParcel(Parcel in) {
            return new ActionItem(in);
        }

        @Override
        public ActionItem[] newArray(int size) {
            return new ActionItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(date);
        parcel.writeString(course);
    }
}

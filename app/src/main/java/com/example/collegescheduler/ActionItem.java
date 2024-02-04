package com.example.collegescheduler;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ActionItem implements Parcelable {
    protected String title;
    protected String date;
    protected String course;
    protected final Items itemType;

    public ActionItem(String title, String date, String course, Items itemType) {
        this.title = title;
        this.date = date;
        this.course = course;
        this.itemType = itemType;
    }

    public ActionItem(Parcel in) {
        title = in.readString();
        date = in.readString();
        course = in.readString();
        itemType = Items.values()[in.readInt()];
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
        parcel.writeInt(itemType.ordinal());
    }

    public String getTitle() {return title;}

    public String getDate() {
        return date;
    }

    public String getCourse() {
        return course;
    }

    public Items getItemType() {
        return itemType;
    }

}

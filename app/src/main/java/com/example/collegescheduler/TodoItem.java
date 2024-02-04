package com.example.collegescheduler;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoItem extends ActionItem implements Parcelable {
    private boolean complete;
    private final boolean isAssignment;

    public TodoItem(String title, String date, String course, boolean isAssignment) {
        super(title, date, course);
        this.complete = false;
        this.isAssignment = isAssignment;
    }

    protected TodoItem(Parcel in) {
        super(in);
        complete = in.readByte() != 0;
        isAssignment = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (complete ? 1 : 0));
        dest.writeByte((byte) (isAssignment ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TodoItem> CREATOR = new Creator<TodoItem>() {
        @Override
        public TodoItem createFromParcel(Parcel in) {
            return new TodoItem(in);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };
}
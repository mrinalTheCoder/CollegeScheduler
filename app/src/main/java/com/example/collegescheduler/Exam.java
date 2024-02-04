package com.example.collegescheduler;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

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

    public CardView modifyCardView(CardView cardView) {
        cardView = super.modifyCardView(cardView);
        TextView textLocation = cardView.findViewById(R.id.textLocation);
        textLocation.setText(this.location);
        return cardView;
    }
}

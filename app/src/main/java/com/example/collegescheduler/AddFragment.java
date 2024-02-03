package com.example.collegescheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegescheduler.databinding.FragmentAddBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class AddFragment extends Fragment {
    private FragmentAddBinding binding;
    private Calendar date;
    private Context context = getActivity();

    TextView title;
    TextView course;
    TextView location;
    TextView professor;
    TextView classSection;

    TextView textView;
    boolean[] selectedDays;
    ArrayList<Integer> daysList = new ArrayList<>();
    String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private FormData formData = new FormData(title, course, location, professor, classSection, date, daysList);

    class FormData{
        TextView title;
        TextView course;
        TextView location;
        TextView professor;
        TextView classSection;

        Calendar date;
        ArrayList<Integer> daysList;
        FormData(TextView title, TextView course, TextView location, TextView professor, TextView classSection, Calendar date, ArrayList<Integer> daysList) {
            this.title = title;
            this.course = course;
            this.location = location;
            this.professor = professor;
            this.classSection = classSection;
            this.date = date;
            this.daysList = daysList;
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentAddBinding.inflate(inflater, container, false);

        textView = binding.textView;
        selectedDays = new boolean[daysArray.length];

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(daysArray, selectedDays, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            daysList.add(i);
                            // Sort array list
                            Collections.sort(daysList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            daysList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < daysList.size(); j++) {
                            // concat array value
                            stringBuilder.append(daysArray[daysList.get(j)]);
                            // check condition
                            if (j != daysList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedDays.length; j++) {
                            // remove all selection
                            selectedDays[j] = false;
                            // clear language list
                            daysList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView title = binding.title;
                TextView course = binding.course;
                TextView location = binding.location;
                TextView professor = binding.professor;
                TextView classSection = binding.classSection;

                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void popDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

}
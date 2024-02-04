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

import java.text.ParseException;
import java.util.Arrays;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class AddFragment extends Fragment {
    private FragmentAddBinding binding;
    //private Calendar date;
    private Context context = getActivity();
    private ArrayList<FormData> formDataArrayList = new ArrayList<>();
    private ArrayList<ActionItem> items;

    private int index;

    private int foundInArrayListIndex = -1;

    String title;
    String course;
    String location;
    String professor;
    String classSection;

    String roomNo;

    String date;

    String time;

    TextView textView;

    private Items formType;
    boolean[] selectedDays;
    ArrayList<Integer> daysList = new ArrayList<>();
    String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private FormData formData;

    class FormData{
        String title;
        String course;
        String location;
        String professor;
        String classSection;
        String date;
        String time;

        Items formType;

        String roomNo;
        ArrayList<Integer> daysList;

        public void setFormData(String title, String course, String location, String professor, String classSection, String date, String time, String roomNo, Items formType, ArrayList<Integer> daysList) {
            this.title = title;
            this.course = course;
            this.location = location;
            this.professor = professor;
            this.classSection = classSection;
            this.date = date;
            this.time = time;
            this.roomNo = roomNo;
            this.daysList = daysList;
            this.formType = formType;
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

        @NonNull AddFragmentArgs args = AddFragmentArgs.fromBundle(getArguments());
        items = args.getActionItems().getParcelableArrayList("action_item");
        index = args.getIndex();
        formType = args.getItemType();

        if (formType == Items.COURSE) {
            binding.form.setText("Course Details");
            binding.textView.setVisibility(View.VISIBLE);
            binding.professor.setVisibility(View.VISIBLE);
            binding.classSection.setVisibility(View.VISIBLE);
            binding.location.setVisibility(View.VISIBLE);
            binding.roomNo.setVisibility(View.VISIBLE);
        } else if (formType == Items.ASSIGNMENT) {
            binding.form.setText("Assignment Details");
        } else if (formType == Items.EXAM) {
            binding.form.setText("Exam Details");
            binding.location.setVisibility(View.VISIBLE);
        } else {
            binding.form.setText("To Do List Details");
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (index <= items.size() - 1) {
//                    binding.textView.setText("");
//                    binding.title.setText("");
//                    binding.course.setText("");
//                    binding.location.setText("");
//                    binding.professor.setText("");
//                    binding.classSection.setText("");
//                    binding.date.setText("");
//                    binding.time.setText("");
//                    binding.roomNo.setText("");
//                }

                title = binding.title.getText().toString();
                course = binding.course.getText().toString();
                location = binding.location.getText().toString();
                professor = binding.professor.getText().toString();
                classSection = binding.classSection.getText().toString();
                date = binding.date.getText().toString();
                time = binding.time.getText().toString();
                roomNo = binding.roomNo.getText().toString();

                FormData formData1 = new FormData();
                formData1.setFormData(title, course, location, professor, classSection, date, time, roomNo, formType, daysList);

                Toast.makeText(getActivity(), "Save successful!",
                        Toast.LENGTH_LONG).show();

                if (index > items.size() - 1) {
                    try {
                        items.add(formDataToActionItem(formData1));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        items.set(index, formDataToActionItem(formData1));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

                binding.textView.setText("");
                binding.title.setText("");
                binding.course.setText("");
                binding.location.setText("");
                binding.professor.setText("");
                binding.classSection.setText("");
                binding.date.setText("");
                binding.time.setText("");
                binding.roomNo.setText("");

                /*
                Log.d("yo", formData1.title);
                Log.d("yo", formData1.course);
                Log.d("yo", formData1.location);
                Log.d("yo", formData1.professor);
                Log.d("yo", formData1.classSection);
                Log.d("yo", formData1.date);
                Log.d("yo", formData1.time);
                Log.d("yo", formData1.roomNo);

                for(Integer i: formData1.daysList) {
                    Log.d("blah", String.valueOf(i));
                }*/

                //NavHostFragment.findNavController(AddFragment.this)
                //.navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    /*public void popDateTimePicker() {
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
    }*/

    public FormData getFormData() {
        return formData;
    }

    public String listToDays(ArrayList<Integer> list) {
        String days = "";
        for (int i : list) {
            if (i == 0) {
                days += "M";
            }
            if (i == 1) {
                days += "T";
            }
            if (i == 2) {
                days += "W";
            }
            if (i == 3) {
                days += "R";
            }
            if (i == 4) {
                days += "F";
            }
            if (i == 5) {
                days += "S";
            }
            if (i == 6) {
                days += "N";
            }
        }
        return days;
    }

    public ActionItem formDataToActionItem(FormData formData) throws ParseException {
        ActionItem item;
        if (formData.formType == Items.COURSE) {
            item = new Course(formData.title, formData.date, listToDays(formData.daysList), formData.course, formData.classSection, formData.professor, formData.location);
        } else if (formData.formType == Items.EXAM) {
            item = new Exam(formData.title, formData.date, formData.course, formData.location);
        } else if (formData.formType == Items.ASSIGNMENT) {
            item = new TodoItem(formData.title, formData.date, formData.course, true);
        } else {
            item = new TodoItem(formData.title, formData.date, formData.course, true);
        }
        return item;
    }

    public ArrayList<FormData> getFormDataArrayList() {
        return formDataArrayList;
    }

    public static AddFragment newInstance() {

        Bundle args = new Bundle();

        AddFragment fragment = new AddFragment();
        fragment.setArguments(args);
        return fragment;
    }

}

package com.example.collegescheduler;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegescheduler.databinding.FragmentAddBinding;

import java.util.ArrayList;
import java.util.Collections;

import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class AddFragment extends Fragment {
    private FragmentAddBinding binding;
    //private Calendar date;
    private Context context = getActivity();
    private ArrayList<FormData> formDataArrayList = new ArrayList<>();
    private static ArrayList<ActionItem> items;

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
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            items = AddFragmentArgs.fromBundle(getArguments()).getActionItems().getParcelableArrayList("action_items");
            formType = AddFragmentArgs.fromBundle(getArguments()).getItemType();
            index = AddFragmentArgs.fromBundle(getArguments()).getIndex();
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

        fillForm();
//        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                Bundle bundle = AddFragmentArgs.fromBundle(getArguments()).getActionItems();
//                AddFragmentDirections.ActionAddFragmentToDisplayFragment action = AddFragmentDirections.actionAddFragmentToDisplayFragment(
//                        formType,
//                        bundle
//                );
//                NavHostFragment.findNavController(AddFragment.this).navigate(action);
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

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
                /*
                items.add(new ActionItem("", "", "", null));
                index = items.size() - 1;



                String[] itemDataTime;
                if (item.getDate().equals("")) {
                    itemDataTime = new String[2];
                } else {
                    itemDataTime = item.getDate().split(" ");
                }*/
                //String[] itemDataTime = {"20", "10"};

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
                    Log.d("IF BLOCK", items.toString());
                    try {
                        items.add(formDataToActionItem(formData1));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                } else {

                    Log.d("ELSE BLOCK", items.toString());
                    try {
                        items.set(index, formDataToActionItem(formData1));

                        Log.d("ELSE BLOCK", items.toString());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

                Bundle newBundle = new Bundle();
                newBundle.putParcelableArrayList("action_items", items);
                AddFragmentDirections.ActionAddFragmentToDisplayFragment action = AddFragmentDirections.actionAddFragmentToDisplayFragment(
                        formType,
                        newBundle
                );
                NavHostFragment.findNavController(AddFragment.this).navigate(action);

                binding.textView.setText("");
                binding.title.setText("");
                binding.course.setText("");
                binding.location.setText("");
                binding.professor.setText("");
                binding.classSection.setText("");
                binding.date.setText("");
                binding.time.setText("");
                binding.roomNo.setText("");

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public FormData getFormData() {
        return formData;
    }

    public String listToDays(ArrayList<Integer> list) {
        String days = "";
        for (int i : list) {
            days += "MTWRFSN".charAt(i);
        }
        return days;
    }

    public ActionItem formDataToActionItem(FormData formData) throws ParseException {
        ActionItem item;
        if (formData.formType == Items.COURSE) {
            item = new Course(formData.title, formData.date + " " + formData.time, listToDays(formData.daysList), formData.course, formData.classSection, formData.professor, formData.location + " " + formData.roomNo);
        } else if (formData.formType == Items.EXAM) {
            item = new Exam(formData.title, formData.date + " " + formData.time, formData.course,formData.location + " " + formData.roomNo);
        } else if (formData.formType == Items.ASSIGNMENT) {
            item = new TodoItem(formData.title, formData.date + " " + formData.time, formData.course, true, Items.ASSIGNMENT);
        } else {
            item = new TodoItem(formData.title, formData.date + " " + formData.time, formData.course, true, Items.TODO);
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

    public void fillForm() {
        if (index <= items.size() - 1) {
            ActionItem item = items.get(index);
            String[] itemDataTime = item.getDate().split("\\s+");
            binding.title.setText(item.getTitle());
            binding.course.setText(item.getCourse());

            if ((items.get(index).itemType == Items.COURSE)) {
                binding.location.setText((((Course) item).getLocation().split("\\s+"))[0]);
            } else {
                binding.location.setText("");
            }
            if ((items.get(index).itemType == Items.EXAM)) {
                binding.location.setText((((Exam) item).getLocation()));
            } else {
                binding.location.setText("");
            }

            if ((items.get(index).itemType == Items.COURSE)) {
                binding.professor.setText(((Course) item).getProf());
            } else {
                binding.professor.setText("");
            }

            binding.classSection.setText(item.getCourse());
            binding.date.setText(itemDataTime[0]);
            binding.time.setText(itemDataTime[1]);

            if ((items.get(index).itemType == Items.COURSE)) {
                binding.roomNo.setText((((Course) item).getLocation().split("\\s+"))[1]);
            } else {
                binding.roomNo.setText("");
            }
            if ((items.get(index).itemType == Items.EXAM)) {
                binding.roomNo.setText((((Exam) item).getLocation()));
            } else {
                binding.roomNo.setText("");
            }

            if ((items.get(index).itemType == Items.COURSE)) {
                binding.textView.setText(((Course) item).getDays());
            } else {
                binding.textView.setText("");
            }
        } else {
            binding.textView.setText("");
            binding.title.setText("");
            binding.course.setText("");
            binding.location.setText("");
            binding.professor.setText("");
            binding.classSection.setText("");
            binding.date.setText("");
            binding.time.setText("");
            binding.roomNo.setText("");
        }
    }

    public static ArrayList<ActionItem> getItem() {
        return items;
    }

}

package com.example.collegescheduler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.TextView;

import com.example.collegescheduler.databinding.FragmentDisplayBinding;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class SortByDate implements Comparator<ActionItem> {

    @Override
    public int compare(ActionItem a, ActionItem b) {
        return a.getDate().compareTo(b.getDate());
    }
}

class SortByCourse implements Comparator<ActionItem> {

    @Override
    public int compare(ActionItem a, ActionItem b) {
        return a.getCourse().compareTo(b.getCourse());
    }
}

class SortByComplete implements Comparator<ActionItem> {

    @Override
    public int compare(ActionItem a, ActionItem b) {
        TodoItem A = (TodoItem) a;
        TodoItem B = (TodoItem) b;
        if (A.isComplete() ^ B.isComplete()) {
            if (A.isComplete() && !B.isComplete()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 0;
        }
    }
}

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayFragment extends Fragment {
    private FragmentDisplayBinding binding;
    private ArrayList<ActionItem> items;
    private Items itemType;

    public DisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayFragment newInstance() {
        DisplayFragment fragment = new DisplayFragment();

        Bundle args = new Bundle();
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            items = DisplayFragmentArgs.fromBundle(getArguments()).getActionItems().getParcelableArrayList("action_items");
            itemType = DisplayFragmentArgs.fromBundle(getArguments()).getItemType();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MaterialToolbar toolbar = (MaterialToolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(itemType.toString().toUpperCase());
        binding = FragmentDisplayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Bundle bundle = DisplayFragmentArgs.fromBundle(getArguments()).getActionItems();
        items = bundle.getParcelableArrayList("action_items");
        itemType = DisplayFragmentArgs.fromBundle(getArguments()).getItemType();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayFragmentDirections.ActionDisplayFragmentToAddFragment action = DisplayFragmentDirections.actionDisplayFragmentToAddFragment(
                        items.size(),
                        itemType,
                        bundle
                );
                NavHostFragment.findNavController(DisplayFragment.this).navigate(action);
            }
        });

        ArrayList<ActionItem> sortedItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemType() == itemType) {
                sortedItems.add(items.get(i));
            }
        }
        RadioGroup radioGroup = view.findViewById(R.id.sortOptions);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                switch (radioButton.getText().toString().toLowerCase()) {
                    case "date":
                        sortedItems.sort(new SortByDate());
                        break;
                    case "course":
                        sortedItems.sort(new SortByCourse());
                        break;
                    case "complete":
                        sortedItems.sort(new SortByComplete());
                        break;
                }
            }
        });
        repopulateCardView(view, sortedItems);
    }

    public void repopulateCardView(View view, ArrayList<ActionItem> items) {
        LinearLayout linearLayout = view.findViewById(R.id.container);
        linearLayout.removeAllViews();

        items.add(new Exam("Exam 1", "20240304", "Math", "Howey"));
        items.add(new Exam("Exam 2", "20240404", "Chem", "Culk"));
        items.add(new Exam("Exam 3", "20240204", "CS", "Skiles"));
        items.add(new Exam("Exam 4", "20240104", "Physics", "Howey"));
        for (ActionItem item : items) {
        // Inflate the content layout for each item
            CardView cardView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.card_view, linearLayout, false);

            // Update the TextViews or other views inside the CardView
            TextView textName = cardView.findViewById(R.id.textName);
            TextView textDate = cardView.findViewById(R.id.textDate);
            TextView textLocation = cardView.findViewById(R.id.textLocation);
            TextView textCourse = cardView.findViewById(R.id.textCourse);

            textName.setText(item.getTitle());
            textDate.setText(item.getDate());
            textCourse.setText(item.getCourse());
            textDate.setText(item.getLocation());

            //textLocation.setText(item.getLocation());
            linearLayout.addView(cardView);
        }

    }
}
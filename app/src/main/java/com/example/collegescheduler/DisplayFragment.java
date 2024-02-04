package com.example.collegescheduler;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.TextView;

import com.example.collegescheduler.databinding.FragmentDisplayBinding;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.ParseException;
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
    private static ArrayList<ActionItem> items = new ArrayList<ActionItem>();

    private Items itemType;
    private ArrayList<ActionItem> sortedItems = new ArrayList<>();

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
            Log.d("ARRAYLIST", items.toString());
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

        if (itemType == Items.COURSE) {
            View myView = view.findViewById(R.id.sortOptions);
            myView.setVisibility(View.GONE);
            myView = view.findViewById(R.id.sortOptionsText);
            myView.setVisibility(View.GONE);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("action_items", items);
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

            // Get the CardView and Modify button by ID
            //Button modifyButton = view.findViewById(getResources().getIdentifier("btnModify" + items.indexOf(item), "id", getActivity().getPackageName()));
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DisplayFragmentDirections.ActionDisplayFragmentToHomeFragment action = DisplayFragmentDirections.actionDisplayFragmentToHomeFragment(
                        bundle
                );
                NavHostFragment.findNavController(DisplayFragment.this).navigate(action);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

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
                repopulateCardView(view, sortedItems);
            }
        });
        try {
            repopulateCardView(view, sortedItems);
        } catch (Exception e ){

        }


    }

    public void repopulateCardView(View view, ArrayList<ActionItem> items) {
        LinearLayout linearLayout = view.findViewById(R.id.container);
        linearLayout.removeAllViews();



        Log.d("INDEX", "" + items.toString());
        for (ActionItem item : sortedItems) {

        // Inflate the content layout for each item
            CardView cardView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.card_view, linearLayout, false);
            cardView.setTag("cardview" + items.indexOf(item));
            ImageButton button = cardView.findViewById(R.id.btnModify);
            button.setTag("btnModify" + items.indexOf(item));

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("action_items", items);

            button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(getActivity(), "Save successful!",
                                Toast.LENGTH_LONG).show();

                        DisplayFragmentDirections.ActionDisplayFragmentToAddFragment action = DisplayFragmentDirections.actionDisplayFragmentToAddFragment(
                                //items.indexOf(item),
                                items.indexOf(item),
                                itemType,
                                bundle
                        );
                        NavHostFragment.findNavController(DisplayFragment.this).navigate(action);
                    }
                });
            linearLayout.addView(item.modifyCardView(cardView));
        }

    }
}
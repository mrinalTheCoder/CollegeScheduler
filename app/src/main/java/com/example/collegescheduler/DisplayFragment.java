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

import com.example.collegescheduler.databinding.FragmentDisplayBinding;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

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

        repopulateCardView(view);
    }

    public void repopulateCardView(View view) {
        LinearLayout linearLayout = view.findViewById(R.id.container);
        linearLayout.removeAllViews();

        for (int i = 0 ; i < 10; i++) {
        // Inflate the content layout for each item
        CardView cardView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.card_view, linearLayout, false);

        // Update the TextViews or other views inside the CardView
        //TextView titleTextView = itemLayout.findViewById(R.id.titleTextView); // Replace with your actual TextView ID
        //TextView descriptionTextView = itemLayout.findViewById(R.id.descriptionTextView); // Replace with your actual TextView ID

        // Set data to the TextViews
        //titleTextView.setText(data.getTitle());
        //descriptionTextView.setText(data.getDescription());

        // Add the item layout to the CardView
        linearLayout.addView(cardView);
    }

    }
}
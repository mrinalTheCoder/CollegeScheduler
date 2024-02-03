package com.example.collegescheduler;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayFragment extends Fragment {

    private ArrayList<ActionItem> items;
    private Items itemType;

    public DisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayFragment newInstance(String param1, String param2) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            items = DisplayFragmentArgs.fromBundle(getArguments()).getActionItems();
            itemType = DisplayFragmentArgs.fromBundle(getArguments()).getItemType();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //items = DisplayFragmentArgs.fromBundle(getArguments()).getActionItems();
        //itemType = DisplayFragmentArgs.fromBundle(getArguments()).getItemType();
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
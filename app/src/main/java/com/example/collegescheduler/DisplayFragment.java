package com.example.collegescheduler;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.collegescheduler.databinding.FragmentDisplayBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
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

public class DisplayFragment extends Fragment {
    private FragmentDisplayBinding binding;
    private static ArrayList<ActionItem> items = new ArrayList<ActionItem>();
    private static ArrayList<ActionItem> sortedItems = new ArrayList<ActionItem>();

    private Items itemType;
    //private ArrayList<ActionItem> sortedItems = new ArrayList<>();

    LinearLayout linearLayout;
    public DisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            items = DisplayFragmentArgs.fromBundle(getArguments()).getActionItems().getParcelableArrayList("action_items");
            itemType = DisplayFragmentArgs.fromBundle(getArguments()).getItemType();
            sortedItems.clear();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getItemType() == itemType || (itemType == Items.TODO && items.get(i).getItemType() != Items.COURSE)) {
                    sortedItems.add(items.get(i));
                }
            }
            Log.d("ARRAYLIST", items.toString());
        }

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("qction_items", items);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.d("back_button", "DisplayFragment back button pressed");
                DisplayFragmentDirections.ActionDisplayFragmentToHomeFragment action = DisplayFragmentDirections.actionDisplayFragmentToHomeFragment(
                        bundle
                );
                NavHostFragment.findNavController(DisplayFragment.this).navigate(action);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MaterialToolbar toolbar = (MaterialToolbar) getActivity().findViewById(R.id.toolbar);
        binding = FragmentDisplayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        linearLayout = view.findViewById(R.id.container);
        if (itemType == Items.COURSE) {
            View myView = view.findViewById(R.id.sortOptions);
            myView.setVisibility(View.GONE);
            myView = view.findViewById(R.id.sortOptionsText);
            myView.setVisibility(View.GONE);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("action_items", items);

        RadioGroup radioGroup = view.findViewById(R.id.sortOptions);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("radio_button", "button changed");

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
                repopulateCardView();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayFragmentDirections.ActionDisplayFragmentToHomeFragment action = DisplayFragmentDirections.actionDisplayFragmentToHomeFragment(
                        bundle
                );
                NavHostFragment.findNavController(DisplayFragment.this).navigate(action);
            }
        });

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

        repopulateCardView();

    }
     private void showConfirmationDialog(String s, ActionItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Confirmation");

        // Set up the buttons
         if (s.equals("modify")) {
             builder.setMessage("Do you want to modify?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("action_items", items);

                    DisplayFragmentDirections.ActionDisplayFragmentToAddFragment action = DisplayFragmentDirections.actionDisplayFragmentToAddFragment(
                            items.indexOf(item),
                            itemType,
                            bundle
                    );
                    NavHostFragment.findNavController(DisplayFragment.this).navigate(action);
                }
            });
         }
         else if (s.equals("complete")) {
             builder.setMessage("Do you want to set as complete/incomplete?");
             builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                     if (item instanceof TodoItem) {
                         ((TodoItem) item).setComplete();
                     }
                     repopulateCardView();
                 }
             });
         } else if (s.equals("delete")) {

             builder.setMessage("Do you want to delete?");
             builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Toast.makeText(getActivity(), "Delete successful!",
                             Toast.LENGTH_SHORT).show();
                     items.remove(item);
                     sortedItems.remove(item);
                     repopulateCardView();
                 }
             });
         }

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle negative button click or do nothing
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void repopulateCardView() {
        linearLayout.removeAllViews();
        for (ActionItem item : sortedItems) {

        // Inflate the content layout for each item
            CardView cardView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.card_view, linearLayout, false);
            cardView.setTag("cardview" + items.indexOf(item));
            ImageButton modifyButton = cardView.findViewById(R.id.btnModify);
            modifyButton.setTag("btnModify" + items.indexOf(item));

            ImageButton deleteButton = cardView.findViewById(R.id.btnDelete);
            deleteButton.setTag("btnDelete" + items.indexOf(item));

            modifyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showConfirmationDialog("modify", item);
                    }
                });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showConfirmationDialog("delete", item);
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showConfirmationDialog("complete", item);
                }
            });
            linearLayout.addView(item.modifyCardView(cardView));
        }

    }
}
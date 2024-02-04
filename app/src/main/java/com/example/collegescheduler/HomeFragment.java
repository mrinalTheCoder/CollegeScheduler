package com.example.collegescheduler;

import android.app.Notification;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<ActionItem> items;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {



        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        items.clear();
//        items.addAll(Arrays.asList(
//                HomeFragmentArgs.fromBundle(getArguments()).getActionItems()
//        ));
        @NonNull HomeFragmentArgs temp = HomeFragmentArgs.fromBundle(getArguments());
        items = temp.getActionItems().getParcelableArrayList("action_item");
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("action_items", items);
        binding.buttonExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToDisplayFragment action = HomeFragmentDirections.actionHomeFragmentToDisplayFragment(
                        Items.EXAM,
                        bundle
                );
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });

        binding.buttonClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToDisplayFragment action = HomeFragmentDirections.actionHomeFragmentToDisplayFragment(
                        Items.COURSE,
                        bundle
                );
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });

        binding.buttonAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToDisplayFragment action = HomeFragmentDirections.actionHomeFragmentToDisplayFragment(
                        Items.ASSIGNMENT,
                        bundle
                );
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });

        binding.buttonToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToDisplayFragment action = HomeFragmentDirections.actionHomeFragmentToDisplayFragment(
                        Items.TODO,
                        bundle
                );
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package com.example.collegescheduler;

import android.app.Notification;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegescheduler.databinding.FragmentHomeBinding;

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

        items = HomeFragmentArgs.fromBundle(getArguments()).getActionItems();

        binding.buttonExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToDisplayFragment action = HomeFragmentDirections.actionHomeFragmentToDisplayFragment(
                    new ArrayList<ActionItem>(),
                    Items.EXAM
                );
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });

        binding.buttonClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToDisplayFragment action = HomeFragmentDirections.actionHomeFragmentToDisplayFragment(
                    items,
                    Items.COURSE
                );
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });

        binding.buttonAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToDisplayFragment action = HomeFragmentDirections.actionHomeFragmentToDisplayFragment(
                    items,
                    Items.ASSIGNMENT
                );
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });

        binding.buttonToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToDisplayFragment action = HomeFragmentDirections.actionHomeFragmentToDisplayFragment(
                    items,
                    Items.TODO
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
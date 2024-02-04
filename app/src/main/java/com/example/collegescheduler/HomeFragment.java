package com.example.collegescheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegescheduler.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

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
        @NonNull Bundle bundle = HomeFragmentArgs.fromBundle(getArguments()).getActionItems();

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
package com.example.collegescheduler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collegescheduler.databinding.FragmentEntryBinding;

import java.util.ArrayList;

public class EntryFragment extends Fragment {
    private FragmentEntryBinding binding;

    public EntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentEntryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("action_items", new ArrayList<ActionItem>());

        binding.entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EntryFragmentDirections.ActionEntryFragmentToHomeFragment action = EntryFragmentDirections.actionEntryFragmentToHomeFragment(
                        bundle
                );
                NavHostFragment.findNavController(EntryFragment.this).navigate(action);
            }
        });
    }
}
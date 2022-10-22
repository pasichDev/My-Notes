package com.pasich.mynotes.ui.view.helloUI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.helloUI.tool.HelloTool;


public class FeaturesFragment extends Fragment {

    private HelloTool helloTool;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helloTool = (HelloTool) getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);
        view.findViewById(R.id.backFragment).setOnClickListener(v -> helloTool.backFragment(2));
        view.findViewById(R.id.backupFragmentNext).setOnClickListener(v -> helloTool.nextFragment(2));
        return view;

    }
}
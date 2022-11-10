package com.pasich.mynotes.ui.helloUI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;


public class FinishFragment extends Fragment {

    private ImageButton saveBackup, finishHello;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);

        saveBackup = view.findViewById(R.id.backupSave);
        finishHello = view.findViewById(R.id.Finish);

        listeners();
        return view;
    }

    private void listeners() {
        saveBackup.setOnClickListener(v -> saveBackup());
        finishHello.setOnClickListener(v -> {
        });
    }

    private void saveBackup() {

    }

    private void finishHello() {

    }
}
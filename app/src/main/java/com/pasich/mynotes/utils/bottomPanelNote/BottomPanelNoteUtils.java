package com.pasich.mynotes.utils.bottomPanelNote;

import android.view.View;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ViewBottomPanelNoteBinding;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;

@ActivityScoped
public class BottomPanelNoteUtils {
    private ViewBottomPanelNoteBinding binding;
    private BottomPanelNoteCallback bottomPanelNoteCallback;

    @Inject
    public BottomPanelNoteUtils() {
    }

    public void setMangerView(View view) {
        this.bottomPanelNoteCallback = (BottomPanelNoteCallback) view.getContext();
        this.binding = ViewBottomPanelNoteBinding.bind(view.findViewById(R.id.bottomPanel));
        setListener();
    }


    private void setListener() {
        binding.addListCheckBox.setOnClickListener(v -> bottomPanelNoteCallback.createListBox());
        binding.addPhotoFiles.setOnClickListener(v -> bottomPanelNoteCallback.addPhotoFiles());
    }


    public void closeActionPanel() {
        binding.addListCheckBox.setOnClickListener(null);
        binding.addPhotoFiles.setOnClickListener(null);
    }
}

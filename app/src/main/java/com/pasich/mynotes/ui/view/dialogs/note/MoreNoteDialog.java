package com.pasich.mynotes.ui.view.dialogs.note;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.ActivitySettings;
import com.pasich.mynotes.base.view.NoteActivityView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.DialogMoreNoteBinding;
import com.pasich.mynotes.utils.GoogleTranslationIntent;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;
import com.pasich.mynotes.utils.prefences.TextStylePreferences;


public class MoreNoteDialog extends DialogFragment {

    private final Note mNote;
    private DialogMoreNoteBinding binding;
    private ActivitySettings activitySettings;
    private NoteActivityView noteActivityView;

    public MoreNoteDialog(Note note) {
        this.mNote = note;
    }

    @SuppressLint("StringFormatMatches")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());

        noteActivityView = (NoteActivityView) getContext();
        activitySettings = (ActivitySettings) getContext();
        binding = DialogMoreNoteBinding.inflate(getLayoutInflater());
        builder.setContentView(binding.getRoot());

        binding.noteInfo.noteInfo.setText(getString(R.string.layoutStringInfoCountSymbols, mNote.getValue().length()));

        initListeners();
        return builder;
    }


    private void initListeners() {
        binding.noSave.setOnClickListener(v -> {
            assert noteActivityView != null;
            noteActivityView.closeActivityNotSaved();
            dismiss();
        });


        if (mNote.getValue().length() >= 5) {
            binding.share.setVisibility(View.VISIBLE);
            binding.share.setOnClickListener(v -> {
                new ShareUtils(mNote, getActivity()).shareNotes();
                dismiss();
            });
            binding.translateNote.setVisibility(View.VISIBLE);
            binding.translateNote.setOnClickListener(v -> {
                new GoogleTranslationIntent().startTranslation(requireActivity(), mNote.getValue());
                dismiss();
            });


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.addShortCutLauncher.setVisibility(View.VISIBLE);
                binding.addShortCutLauncher.setOnClickListener(v -> {
                    ShortCutUtils.createShortCut(mNote, getContext());
                    dismiss();
                });
            }
            binding.moveToTrash.setOnClickListener(v -> {
                assert noteActivityView != null;
                noteActivityView.deleteNote(mNote);
                dismiss();
            });
        }

        initializeSettingsView();
    }

    private void initializeSettingsView() {
        TextStylePreferences textStyle = new TextStylePreferences(binding.viewSettingsNote.textStyleItem);
        binding.viewSettingsNote.textStyleItem.setOnClickListener(v -> {
            textStyle.changeArgument();
            activitySettings.changeTextStyle();
        });
    }

}

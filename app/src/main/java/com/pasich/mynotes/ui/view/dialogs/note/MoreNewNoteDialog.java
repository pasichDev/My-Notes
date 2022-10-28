package com.pasich.mynotes.ui.view.dialogs.note;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.NoteActivityView;
import com.pasich.mynotes.data.old.notes.Note;
import com.pasich.mynotes.utils.GoogleTranslationIntent;
import com.pasich.mynotes.utils.ShareUtils;


public class MoreNewNoteDialog extends DialogFragment {

    private final Note mNote;

    public MoreNewNoteDialog(Note note) {
        this.mNote = note;
    }

    @SuppressLint("StringFormatMatches")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final NoteActivityView noteActivityView = (NoteActivityView) getContext();
        builder.setContentView(R.layout.dialog_more_new_note);

        builder.findViewById(R.id.noSave).setOnClickListener(v -> {
            assert noteActivityView != null;
            noteActivityView.closeActivityNotSaved();
            dismiss();
        });


        if (mNote.getValue().length() >= 5) {
            builder.findViewById(R.id.share).setVisibility(View.VISIBLE);
            builder.findViewById(R.id.share).setOnClickListener(v -> {
                new ShareUtils(mNote, getActivity()).shareNotes();
                dismiss();
            });
            builder.findViewById(R.id.translateNote).setVisibility(View.VISIBLE);
            builder.findViewById(R.id.translateNote).setOnClickListener(v -> {
                new GoogleTranslationIntent().startTranslation(requireActivity(), mNote.getValue());
                dismiss();
            });

        }

        return builder;
    }

}

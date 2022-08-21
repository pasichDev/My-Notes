package com.pasich.mynotes.ui.view.dialogs.note;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.NoteActivityView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.utils.GoogleTranslationIntent;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;


public class MoreNoteDialog extends DialogFragment {

    private final Note mNote;
    private final boolean typeActivity;

    public MoreNoteDialog(Note note, boolean typeNote) {
        this.mNote = note;
        this.typeActivity = typeNote;
    }

    @SuppressLint("StringFormatMatches")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final NoteActivityView noteActivityView = (NoteActivityView) getContext();
        builder.setContentView(typeActivity ? R.layout.dialog_more_new_note : R.layout.dialog_more_note);


        if (!typeActivity) {
            MaterialTextView infoItem = builder.findViewById(R.id.noteInfo);
            assert infoItem != null;
            infoItem.setText(getString(R.string.layoutStringInfoCountSymbols, mNote.getValue().length()));
        }


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

            if (!typeActivity) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    builder.findViewById(R.id.addShortCutLauncher).setVisibility(View.VISIBLE);
                    builder.findViewById(R.id.addShortCutLauncher).setOnClickListener(v -> {
                        ShortCutUtils.createShortCut(mNote, getContext());
                        dismiss();
                    });
                }
                builder.findViewById(R.id.moveToTrash).setOnClickListener(v -> {
                    assert noteActivityView != null;
                    noteActivityView.deleteNote(mNote);
                    dismiss();
                });
            }


        }

        return builder;
    }

}

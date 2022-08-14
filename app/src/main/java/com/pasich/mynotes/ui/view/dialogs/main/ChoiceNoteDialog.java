package com.pasich.mynotes.ui.view.dialogs.main;

import static com.pasich.mynotes.utils.ListNotesUtils.convertDate;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;

public class ChoiceNoteDialog extends BottomSheetDialogFragment {

    private final Note note;

    public ChoiceNoteDialog(Note note) {
        this.note = note;
    }

    @SuppressLint("StringFormatMatches")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
        final NoteView noteView = (NoteView) getContext();
        builder.setContentView(R.layout.dialog_choice_note);

        MaterialTextView infoItem = builder.findViewById(R.id.noteInfo);
        assert infoItem != null;
        infoItem.setText(getString(R.string.layoutStringInfo, convertDate(note.getDate()), note.getValue().length()));

        builder.findViewById(R.id.action_panel_activate).setOnClickListener(view ->
        {
            assert noteView != null;
            noteView.actionStartNote();
            dismiss();
        });
        builder.findViewById(R.id.shareLinearLayout).setOnClickListener(view -> {
                    new ShareUtils(note, getActivity()).shareNotes();
                    dismiss();
                }
        );

        builder.findViewById(R.id.tagLinearLayout).setOnClickListener(view -> {
            assert noteView != null;
            noteView.tagNoteSelected(note);
            dismiss();
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LinearLayout addNoteForDesktop = builder.findViewById(R.id.addNoteForDesktop);
            assert addNoteForDesktop != null;
            addNoteForDesktop.setVisibility(View.VISIBLE);
            addNoteForDesktop.setOnClickListener(view -> {
                ShortCutUtils.createShortCut(note, getContext());
                dismiss();
            });
        }

        builder.findViewById(R.id.moveToTrash).setOnClickListener(view -> {
            assert noteView != null;
            noteView.deleteNote(note);
            dismiss();
        });

        return builder;
    }

}

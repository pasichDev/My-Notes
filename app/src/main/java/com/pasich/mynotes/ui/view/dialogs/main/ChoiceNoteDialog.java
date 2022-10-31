package com.pasich.mynotes.ui.view.dialogs.main;

import static com.pasich.mynotes.utils.FormattedDataUtil.lastDayEditNote;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.DialogOpenVibrate;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.DialogChoiceNoteBinding;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;

public class ChoiceNoteDialog extends DialogOpenVibrate {

    private final Note note;
    private final int positionItem;

    public ChoiceNoteDialog(Note note, int position) {
        this.note = note;
        this.positionItem = position;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog mDialog = new BottomSheetDialog(requireActivity());
        final NoteView noteView = (NoteView) getContext();
        mDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        com.pasich.mynotes.databinding.DialogChoiceNoteBinding binding = DialogChoiceNoteBinding.inflate(getLayoutInflater());

        mDialog.setContentView(binding.getRoot());


        MaterialTextView infoItem = mDialog.findViewById(R.id.noteInfo);
        assert infoItem != null;
        infoItem.setText(getString(R.string.layoutStringInfo, lastDayEditNote(note.getDate()), String.valueOf(note.getValue().length())));

        binding.actionPanelActivate.setOnClickListener(view ->
        {
            assert noteView != null;
            noteView.actionStartNote(note, positionItem);
            dismiss();
        });
        binding.shareLinearLayout.setOnClickListener(view -> {
                    new ShareUtils(note, getActivity()).shareNotes();
                    dismiss();
                }
        );

        binding.tagLinearLayout.setOnClickListener(view -> {
            assert noteView != null;
            noteView.tagNoteSelected(note);
            dismiss();
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            binding.addNoteForDesktop.setVisibility(View.VISIBLE);
            binding.addNoteForDesktop.setOnClickListener(view -> {
                ShortCutUtils.createShortCut(note, getContext());
                dismiss();
            });
        }

        binding.moveToTrash.setOnClickListener(view -> {
            assert noteView != null;
            noteView.deleteNote(note);
            dismiss();
        });

        return mDialog;
    }

}

package com.pasich.mynotes.ui.view.dialogs.note;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.model.SourceModel;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;
import com.pasich.mynotes.utils.adapters.NoteSourceAdapter;

import java.util.ArrayList;

public class SourceNoteDialog extends DialogFragment {
    private final ArrayList<SourceModel> listSource;

    public SourceNoteDialog(ArrayList<SourceModel> listSource) {
        this.listSource = listSource;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final ListDialogView listDialogView = new ListDialogView(getLayoutInflater());

        listDialogView.addTitle(getString(R.string.sourceNotes));
        listDialogView.getItemsView().setAdapter(new NoteSourceAdapter(listSource));
        listDialogView.getItemsView().setOnItemClickListener(
                (parent, v, position, id) -> {

                    dismiss();
                });
        listDialogView.getRootContainer().addView(listDialogView.getItemsView());
        builder.setContentView(listDialogView.getRootContainer());
        return builder;
    }

}

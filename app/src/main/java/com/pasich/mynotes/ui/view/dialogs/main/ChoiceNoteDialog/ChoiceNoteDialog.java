package com.pasich.mynotes.ui.view.dialogs.main.ChoiceNoteDialog;

import static com.pasich.mynotes.utils.ListNotesUtils.convertDate;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.data.model.ChoiceModel;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.ViewInfoItemBinding;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class ChoiceNoteDialog extends DialogFragment {

    private final Note note;

    public ChoiceNoteDialog(Note note) {
        this.note = note;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());

        final ListDialogView view = new ListDialogView(getLayoutInflater());
        final NoteView noteView = (NoteView) getContext();


        view.addView(view.getItemsView());
        view.addView(initializeInfoLayout(convertDate(note.getDate()), note.getValue().length()).getRoot());

        DialogListAdapter adapter = new DialogListAdapter(initList());
        view.getItemsView().setAdapter(adapter);


        view.getItemsView()
                .setOnItemClickListener(
                        (parent, v, position, id) -> {
                            if (adapter.getItem(position).getAction().equals("SelectAll")) {
                                assert noteView != null;
                                noteView.actionStartNote();
                            }
                            if (adapter.getItem(position).getAction().equals("Share")) {
                                new ShareUtils(note, getActivity()).shareNotes();
                            }

                            if (adapter.getItem(position).getAction().equals("Tag")) {
                                assert noteView != null;
                                noteView.tagNoteSelected(note);
                            }

                            if (adapter.getItem(position).getAction().equals("Delete")) {
                                assert noteView != null;
                                noteView.deleteNote(note);
                            }
                            if (adapter.getItem(position).getAction().equals("addShotCut") &&
                                    android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                ShortCutUtils.createShortCut(note, getContext());
                            }
                            dismiss();
                        });

        builder.setContentView(view.getRootContainer());

        return builder;
    }

    private ArrayList<ChoiceModel> initList() {
        final ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
        arrayChoice.add(
                new ChoiceModel(
                        getString(R.string.selectAll), R.drawable.ic_check_box, "SelectAll", false));
        arrayChoice.add(
                new ChoiceModel(getString(R.string.share), R.drawable.ic_share, "Share", false));
        arrayChoice.add(new ChoiceModel(getString(R.string.tag), R.drawable.ic_tag, "Tag", false));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            arrayChoice.add(new ChoiceModel(getString(R.string.addShortCutLauncher), R.drawable.ic_label, "addShotCut", false));
        }
        arrayChoice.add(
                new ChoiceModel(getString(R.string.trashNotes), R.drawable.ic_delete_note, "Delete", false));

        return arrayChoice;
    }

    @SuppressLint("StringFormatMatches")
    public ViewInfoItemBinding initializeInfoLayout(String dateNote, int symbolsLength) {
        ViewInfoItemBinding binding = ViewInfoItemBinding.inflate(getLayoutInflater());
        binding.infoTextView.setText(getString(R.string.layoutStringInfo, dateNote, symbolsLength));
        return binding;
    }
}
